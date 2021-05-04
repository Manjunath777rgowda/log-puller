/* Copyright(C) 2020-21. Nuvepro Pvt. Ltd. All rights reserved */

package com.manjunath.logpuller.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.manjunath.logpuller.constants.ErrorMessages;
import com.manjunath.logpuller.exceptions.DataException;

import lombok.extern.slf4j.Slf4j;

/**
 * This util is used perform the file related operations
 */
@Slf4j
public class FileUtil {

    private static final String basePath = Paths.get(".").toAbsolutePath().normalize().toString();
    public static final String temp = basePath + "/temp/";

    /**
     * This method is used to convert the exception to the json string to write into the file
     *
     * @param exception
     */
    public static void createStackTraceFile( Exception exception )
    {
        try
        {
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap errorHashMap = objectMapper.readValue(objectMapper.writeValueAsString(exception), HashMap.class);
            List<HashMap<String, String>> stackTrace = (List) errorHashMap.get("stackTrace");
            List<HashMap<String, String>> newStackTrace = new ArrayList<>();

            for( HashMap<String, String> entry : stackTrace )
            {
                if( entry.get("className").contains("nuvelink") )
                    newStackTrace.add(entry);
            }

            errorHashMap.put("stackTrace", newStackTrace);
            errorHashMap.remove("cause");
            errorHashMap.remove("suppressed");

            writeJsonFile(errorHashMap, MDC.get("logId"));
        }
        catch( Exception e )
        {
            log.error("Error while writing Exception to file", e);
        }
    }

    /**
     * This method is used to write the json file with the pretty printer
     *
     * @param content
     * @param fileName
     */
    private static void writeJsonFile( Object content, String fileName )
    {
        try
        {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
            objectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);

            File theDir = new File(temp);
            if( !theDir.exists() )
                theDir.mkdir();

            String absolutePath = temp + fileName + ".json";
            FileWriter fw = new FileWriter(absolutePath, true);
            fw.write(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(content));
            fw.close();

            new File(absolutePath);
        }
        catch( Exception e )
        {
            log.error("Error while writing json file", e);
        }
    }

    public static void deleteFile( File file )
    {
        try
        {
            if( file.isDirectory() )
                FileUtils.deleteDirectory(file);
            else
                file.delete();
        }
        catch( IOException e )
        {
            log.error(ErrorMessages.SOMETHING_WENT_WRONG);
        }
    }

    public static void createFolder( String folderPath ) throws DataException
    {
        File directory = new File(folderPath);

        if( !directory.exists() )
        {
            log.info("creating directory: " + directory.getAbsolutePath());
            try
            {
                if( directory.mkdir() )
                    log.info(" directory {} created ", directory.getAbsoluteFile());
                else
                {
                    log.error("unable to create the directory : " + directory.getAbsoluteFile());
                }
            }
            catch( Exception e )
            {
                log.error("unable to create the directory : " + directory.getAbsoluteFile());
                throw new DataException(e);
            }
        }

    }

    /**
     * This method is used to validate the file
     *
     * @param fileName
     * @throws DataException
     */
    public static void validateFile( String fileName ) throws DataException
    {
        if( NullEmptyUtils.isNullOrEmpty(fileName) )
            throw new DataException(ErrorMessages.INVALID_FILE_NAME, HttpStatus.BAD_REQUEST);

        File file = new File(fileName);

        if( !file.exists() )
            throw new DataException(ErrorMessages.FILE_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    public static String readFile( String fileName ) throws DataException
    {
        try
        {
            validateFile(fileName);
            return new String(Files.readAllBytes(Paths.get(fileName)));
        }
        catch( IOException e )
        {
            log.error("Error while reading file", e);
            throw new DataException(ErrorMessages.ERROR_WHILE_READING_FILE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static void writeFile( String fileName, String fileContent ) throws DataException
    {
        try
        {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(fileContent);
            fileWriter.close();
        }
        catch( IOException e )
        {
            log.error("Error while reading file", e);
            throw new DataException(ErrorMessages.ERROR_WHILE_READING_FILE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
