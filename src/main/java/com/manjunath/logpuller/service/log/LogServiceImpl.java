package com.manjunath.logpuller.service.log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.manjunath.logpuller.exceptions.DataException;
import com.manjunath.logpuller.representation.request.GetLogRequestToGrayLog;
import com.manjunath.logpuller.representation.request.GrayLogBean;
import com.manjunath.logpuller.representation.request.GrayLogOutput;
import com.manjunath.logpuller.representation.request.GraylogList;
import com.manjunath.logpuller.restclient.GraylogClient;
import com.manjunath.logpuller.utils.FileUtil;
import com.manjunath.logpuller.utils.NullEmptyUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private GraylogClient graylogClient;

    @Autowired
    private Environment environment;

    private static String csvFolder;
    private static String jsonFolder;
    private static final String owner = "owner";
    private static final String parent = "parent";
    private static final String children = "children";
    private static final String logIdFiled = "logId";
    private static final String requestId = "requesterId";
    //    private static final String catalog = "acdbe21b-edf4-4c63-b5f0-63614731a537";
    //    private static final String apiGateway = "aa198512-f2c7-4353-8de0-89e5faffdb05";
    //    private static final String nlApplication = "cc65576e-bdd6-4cf1-91e1-84fdd379bfc1";

    @Override
    public GrayLogOutput getLogs( String logId ) throws DataException
    {
        GrayLogOutput grayLogOutput = new GrayLogOutput();

        String appFolder = environment.getProperty("app.folder");
        File folder = new File(appFolder + logId);
        if( folder.exists() )
            FileUtil.deleteFile(folder);

        csvFolder = folder.getAbsolutePath() + File.separator + "csv";
        jsonFolder = folder.getAbsolutePath() + File.separator + "json";

        FileUtil.createFolder(folder.getAbsolutePath());
        FileUtil.createFolder(csvFolder);
        FileUtil.createFolder(jsonFolder);

        GraylogList graylogOwner = getLogFromGraylog(logId, logIdFiled);
        grayLogOutput.setOwner(graylogOwner);
        getParentsAndChildren(grayLogOutput, parent);
        getParentsAndChildren(grayLogOutput, children);

        return grayLogOutput;
    }

    private GraylogList getLogFromGraylog( String logId, String filedName ) throws DataException
    {
        GetLogRequestToGrayLog getLogRequestToGrayLog = new GetLogRequestToGrayLog();
        getLogRequestToGrayLog.setTimerange(new GetLogRequestToGrayLog.TimeRange(0, "relative"));
        getLogRequestToGrayLog
                .setQuery_string(new GetLogRequestToGrayLog.Query_string("elasticsearch", filedName + ":" + logId));
        getLogRequestToGrayLog.setFields_in_order(Arrays.asList("className", "endpoint", "environment", "facility",
                "full_message", "inbound_json", "ip", "loggerLevel", "logId", "message", "requesterId", "requestMethod",
                "server", "server_fqdn", "service", "simpleClassName", "source", "StackTrace", "tenant", "timestamp",
                "userId", "username"));

        String logs = graylogClient.getLogs(getBasicAuthToken(), environment.getProperty("graylog-user"),
                getLogRequestToGrayLog);
        if( !NullEmptyUtils.isNullOrEmpty(logs) )
        {

            //write CSV folder
            String csvTemp = csvFolder + File.separator + "temp.csv";
            String jsonTemp = jsonFolder + File.separator + "temp.json";

            FileUtil.writeFile(csvTemp, logs);

            writeJsonFromCsv(csvTemp, jsonTemp);
            GraylogList graylogList = readJsonFile(jsonTemp);

            String csvFile = csvFolder + File.separator + graylogList.getLogId() + ".csv";
            String jsonFileName = jsonFolder + File.separator + graylogList.getLogId() + ".json";

            boolean renamedCsv = new File(csvTemp).renameTo(new File(csvFile));
            boolean renamedJson = new File(jsonTemp).renameTo(new File(jsonFileName));

            if( !renamedCsv )
                log.error("Error renaming CSV from {} to {}", csvTemp, csvFile);

            if( !renamedJson )
                log.error("Error renaming JSON from {} to {}", jsonTemp, jsonFileName);

            return graylogList;
        }
        return null;
    }

    private void getParentsAndChildren( GrayLogOutput grayLogOutput, String relationLevel ) throws DataException
    {
        boolean parentFound = false;

        if( !NullEmptyUtils.isNull(grayLogOutput.getOwner()) )
        {
            for( GrayLogBean graylog : grayLogOutput.getOwner().getGrayLogBeanList() )
            {
                if( !NullEmptyUtils.isNullOrEmpty(graylog.getRequesterId()) && !parentFound )
                {
                    GraylogList owner;

                    if( relationLevel.equals(parent) )
                        owner = getLogFromGraylog(graylog.getRequesterId(), logIdFiled);
                    else
                        owner = getLogFromGraylog(graylog.getLogId(), requestId);

                    if( !NullEmptyUtils.isNull(owner) )
                    {
                        parentFound = true;

                        GrayLogOutput grayLogOutput1 = new GrayLogOutput();
                        grayLogOutput1.setOwner(owner);

                        if( relationLevel.equals(parent) )
                            grayLogOutput.setParent(grayLogOutput1.getOwner());

                        if( relationLevel.equals(children) )
                            grayLogOutput.setChildren(grayLogOutput1.getOwner());

                        getParentsAndChildren(grayLogOutput1, relationLevel);
                    }
                }
            }
        }
    }

    private String getBasicAuthToken()
    {
        byte[] encodedBytes = Base64Utils
                .encode((environment.getProperty("graylog-user") + ":" + environment.getProperty("graylog-password"))
                        .getBytes());
        return "Basic " + new String(encodedBytes);
    }

    private void writeJsonFromCsv( String csvFileName, String jsonFileName )
    {
        CsvSchema orderLineSchema = CsvSchema.emptySchema().withHeader();
        CsvMapper csvMapper = new CsvMapper();
        try
        {
            MappingIterator<?> orderLines = csvMapper.readerWithSchemaFor(HashMap.class).with(orderLineSchema)
                    .readValues(new File(csvFileName));

            new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true).writeValue(new File(jsonFileName),
                    orderLines.readAll());
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
    }

    private GraylogList readJsonFile( String jsonFileName ) throws DataException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

        File file = new File(jsonFileName);

        try
        {
            List list = objectMapper.readValue(file, List.class);

            if( NullEmptyUtils.isNullOrEmpty(list) )
                throw new DataException("Invalid Json : " + jsonFileName, HttpStatus.BAD_REQUEST);

            List<GrayLogBean> grayLogBeans = new ArrayList<>();
            for( Object object : list )
            {
                String grayLogBeanJson = objectMapper.writeValueAsString(object);
                GrayLogBean grayLogBean = objectMapper.readValue(grayLogBeanJson, GrayLogBean.class);
                grayLogBeans.add(grayLogBean);
            }

            GraylogList graylogList = new GraylogList();
            graylogList.setGrayLogBeanList(grayLogBeans);
            graylogList.setLogId(graylogList.getGrayLogBeanList().get(0).getLogId());
            graylogList.setServiceName(graylogList.getGrayLogBeanList().get(0).getService());

            //todo need to set request and response data

            return graylogList;
        }
        catch( IOException e )
        {
            log.error("Error while reading json", e);
            throw new DataException("Error while reading json", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
