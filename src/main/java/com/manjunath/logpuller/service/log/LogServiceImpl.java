package com.manjunath.logpuller.service.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.manjunath.logpuller.exceptions.DataException;
import com.manjunath.logpuller.representation.request.GetLogRequestToGrayLog;
import com.manjunath.logpuller.representation.request.GrayLogBean;
import com.manjunath.logpuller.representation.request.ServiceLogNode;
import com.manjunath.logpuller.restclient.GraylogClient;
import com.manjunath.logpuller.utils.FileUtil;
import com.manjunath.logpuller.utils.NullEmptyUtils;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private GraylogClient graylogClient;

    //    @Autowired
    //    private MockGraylogService graylogClient;

    @Autowired
    private Environment environment;

    private static String csvFolder;
    private static String jsonFolder;
    private static final String children = "children";
    private static final String logIdFiled = "logId";
    private static final String requestId = "requesterId";
    private static final String client = "CLIENT";
    private ServiceLogNode rootNode;
    //    private static final String catalog = "acdbe21b-edf4-4c63-b5f0-63614731a537";
    //    private static final String apiGateway = "aa198512-f2c7-4353-8de0-89e5faffdb05";
    //    private static final String nlApplication = "cc65576e-bdd6-4cf1-91e1-84fdd379bfc1";

    @Override
    public ServiceLogNode getLogs( String logId ) throws DataException
    {
        rootNode = new ServiceLogNode();
        String appFolder = environment.getProperty("app.folder");
        File folder = new File(appFolder + logId);
        if( folder.exists() )
            FileUtil.deleteFile(folder);

        csvFolder = folder.getAbsolutePath() + File.separator + "csv" + File.separator;
        jsonFolder = folder.getAbsolutePath() + File.separator + "json" + File.separator;

        FileUtil.createFolder(folder.getAbsolutePath());
        FileUtil.createFolder(csvFolder);
        FileUtil.createFolder(jsonFolder);

        //Find the root node first
        findRootNode(logId);

        if( NullEmptyUtils.isNullOrEmpty(rootNode.getLogId()) )
            throw new DataException("Invalid Log Id : " + logId, HttpStatus.BAD_REQUEST);

        getChildNodes(rootNode, logId);

        ServiceLogNode serviceLogNode = new ServiceLogNode();
        serviceLogNode.setServiceName(client);
        serviceLogNode.setChildren(Collections.singletonList(rootNode));
        return serviceLogNode;
    }

    private void getChildNodes( ServiceLogNode serviceLogNode, String logId ) throws DataException
    {
        if( !NullEmptyUtils.isNull(serviceLogNode) )
        {
            getLogsByRequesterId(serviceLogNode);
            serviceLogNode.setDefault(logId.equals(serviceLogNode.getLogId()));
            if( !NullEmptyUtils.isNullOrEmpty(serviceLogNode.getChildren()) )
            {
                for( ServiceLogNode child : serviceLogNode.getChildren() )
                {
                    getChildNodes(child, logId);
                }
            }
        }
    }

    private void findRootNode( String logId ) throws DataException
    {
        ServiceLogNode logFromGraylog = getLogsByLogId(logId);
        if( !NullEmptyUtils.isNull(logFromGraylog) )
        {
            if( !NullEmptyUtils.isNullOrEmpty(logFromGraylog.getGrayLogBeanList().get(0).getRequesterId()) )
            {
                findRootNode(logFromGraylog.getGrayLogBeanList().get(0).getRequesterId());
            }
            else
                rootNode = logFromGraylog;
        }
    }

    private ServiceLogNode getLogsByLogId( String logId ) throws DataException
    {
        GetLogRequestToGrayLog getLogRequestToGrayLog = new GetLogRequestToGrayLog();
        getLogRequestToGrayLog.setTimerange(new GetLogRequestToGrayLog.TimeRange(0, "relative"));
        getLogRequestToGrayLog.setQuery_string(
                new GetLogRequestToGrayLog.Query_string("elasticsearch", LogServiceImpl.logIdFiled + ":" + logId));
        getLogRequestToGrayLog.setFields_in_order(Arrays.asList("className", "endpoint", "environment", "facility",
                "full_message", "inbound_json", "ip", "loggerLevel", "logId", "message", "requesterId", "requestMethod",
                "server", "server_fqdn", "service", "simpleClassName", "source", "StackTrace", "tenant", "timestamp",
                "userId", "username"));

        String logs = graylogClient.getLogs(getBasicAuthToken(), environment.getProperty("graylog-user"),
                getLogRequestToGrayLog);
        if( !NullEmptyUtils.isNullOrEmpty(logs) )
        {
            List<GrayLogBean> grayLogBeans = convertCSVToPojo(logs);
            HashMap<String, List<GrayLogBean>> fileHashMap = new HashMap<>();

            for( GrayLogBean grayLogBean : grayLogBeans )
            {
                if( NullEmptyUtils.isNullOrEmpty(fileHashMap.get(grayLogBean.getLogId())) )
                {
                    List<GrayLogBean> list = new ArrayList<>();
                    list.add(grayLogBean);
                    fileHashMap.put(grayLogBean.getLogId(), list);
                }
                else
                    fileHashMap.get(grayLogBean.getLogId()).add(grayLogBean);
            }
            writeFiles(fileHashMap);

            ServiceLogNode serviceLogNode = new ServiceLogNode();
            serviceLogNode.setGrayLogBeanList(grayLogBeans);
            serviceLogNode.setLogId(logId);
            serviceLogNode.setServiceName(grayLogBeans.get(0).getService());

            return serviceLogNode;
        }
        return null;
    }

    private void getLogsByRequesterId( ServiceLogNode serviceLogNode ) throws DataException
    {
        GetLogRequestToGrayLog getLogRequestToGrayLog = new GetLogRequestToGrayLog();
        getLogRequestToGrayLog.setTimerange(new GetLogRequestToGrayLog.TimeRange(0, "relative"));
        getLogRequestToGrayLog.setQuery_string(new GetLogRequestToGrayLog.Query_string("elasticsearch",
                LogServiceImpl.requestId + ":" + serviceLogNode.getLogId()));
        getLogRequestToGrayLog.setFields_in_order(Arrays.asList("className", "endpoint", "environment", "facility",
                "full_message", "inbound_json", "ip", "loggerLevel", "logId", "message", "requesterId", "requestMethod",
                "server", "server_fqdn", "service", "simpleClassName", "source", "StackTrace", "tenant", "timestamp",
                "userId", "username"));

        String logs = graylogClient.getLogs(getBasicAuthToken(), environment.getProperty("graylog-user"),
                getLogRequestToGrayLog);
        if( !NullEmptyUtils.isNullOrEmpty(logs) )
        {
            List<GrayLogBean> grayLogBeans = convertCSVToPojo(logs);
            HashMap<String, List<GrayLogBean>> fileHashMap = new HashMap<>();

            for( GrayLogBean grayLogBean : grayLogBeans )
            {
                if( NullEmptyUtils.isNullOrEmpty(fileHashMap.get(grayLogBean.getLogId())) )
                {
                    List<GrayLogBean> list = new ArrayList<>();
                    list.add(grayLogBean);
                    fileHashMap.put(grayLogBean.getLogId(), list);
                }
                else
                    fileHashMap.get(grayLogBean.getLogId()).add(grayLogBean);
            }
            writeFiles(fileHashMap);

            List<ServiceLogNode> childNodeList = new ArrayList<>();

            for( Map.Entry<String, List<GrayLogBean>> entry : fileHashMap.entrySet() )
            {
                ServiceLogNode childNode = new ServiceLogNode();
                childNode.setGrayLogBeanList(entry.getValue());
                childNode.setLogId(entry.getKey());
                childNode.setServiceName(childNode.getGrayLogBeanList().get(0).getService());
                childNodeList.add(childNode);
            }

            serviceLogNode.setChildren(childNodeList);
        }
    }

    private void writeFiles( HashMap<String, List<GrayLogBean>> fileHashMap ) throws DataException
    {
        try
        {
            for( Map.Entry<String, List<GrayLogBean>> entry : fileHashMap.entrySet() )
            {
                //write Json
                FileUtil.writeFile(jsonFolder + entry.getKey() + ".json",
                        new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(entry.getValue()));

                //write CSV
                writeCSV(csvFolder + entry.getKey() + ".csv", entry.getValue());
            }
        }
        catch( Exception e )
        {
            log.error("Error while writing files", e);
            throw new DataException("Error while writing files", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void writeCSV( String csvFileName, List<GrayLogBean> grayLogBeans ) throws DataException
    {
        try
        {
            // Creating writer class to generate
            // csv file
            FileWriter writer = new FileWriter(csvFileName);

            // Create Mapping Strategy to arrange the
            // column name in order
            ColumnPositionMappingStrategy mappingStrategy = new ColumnPositionMappingStrategy();
            mappingStrategy.setType(GrayLogBean.class);

            // Arrange column name as provided in below array.
            Field[] fields = GrayLogBean.class.getDeclaredFields();
            String[] columns = new String[fields.length];
            for( int i = 0; i < fields.length; i++ )
                columns[i] = fields[i].toString();

            mappingStrategy.setColumnMapping(columns);

            // Creating StatefulBeanToCsv object
            StatefulBeanToCsvBuilder<GrayLogBean> builder = new StatefulBeanToCsvBuilder(writer);
            StatefulBeanToCsv beanWriter = builder.withMappingStrategy(mappingStrategy).build();

            // Write list to StatefulBeanToCsv object
            beanWriter.write(grayLogBeans);

            // closing the writer object
            writer.close();
        }
        catch( Exception e )
        {
            log.error("Error while writing CSV file", e);
            throw new DataException("Error while writing CSV file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String getBasicAuthToken()
    {
        byte[] encodedBytes = Base64Utils
                .encode((environment.getProperty("graylog-user") + ":" + environment.getProperty("graylog-password"))
                        .getBytes());
        return "Basic " + new String(encodedBytes);
    }

    private List<GrayLogBean> convertCSVToPojo( String csvLogs ) throws DataException
    {
        CsvSchema orderLineSchema = CsvSchema.emptySchema().withHeader();
        CsvMapper csvMapper = new CsvMapper();
        try
        {
            MappingIterator<?> orderLines = csvMapper.readerWithSchemaFor(HashMap.class).with(orderLineSchema)
                    .readValues(csvLogs);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
            objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

            List<GrayLogBean> grayLogBeans = new ArrayList<>();
            for( Object object : orderLines.readAll() )
            {
                String grayLogBeanJson = objectMapper.writeValueAsString(object);
                GrayLogBean grayLogBean = objectMapper.readValue(grayLogBeanJson, GrayLogBean.class);
                grayLogBeans.add(grayLogBean);
            }

            return grayLogBeans;
        }
        catch( IOException e )
        {
            log.error("Error while converting CSV string to JSON string", e);
            throw new DataException("Error while converting CSV string to JSON string",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
