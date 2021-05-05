package com.manjunath.logpuller.service.mockgraylog;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.manjunath.logpuller.exceptions.DataException;
import com.manjunath.logpuller.representation.request.GetLogRequestToGrayLog;
import com.manjunath.logpuller.utils.FileUtil;
import com.manjunath.logpuller.utils.NullEmptyUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MockGraylogService {

    private static final String logIdField = "logId";
    private static final String requesterIdField = "requesterId";
    private static final String mockLogIdFolder = "logpuller-input/logId/";
    private static final String mockRequestIdFolder = "logpuller-input/requesterId/";

    public String getLogs( String token, String requestedBy, GetLogRequestToGrayLog request )
    {
        try
        {
            if( NullEmptyUtils.isNullOrEmpty(token) )
                throw new DataException("Token can not be empty", HttpStatus.BAD_REQUEST);

            if( NullEmptyUtils.isNullOrEmpty(requestedBy) )
                throw new DataException("Requested by can not be empty", HttpStatus.BAD_REQUEST);

            String query_string = request.getQuery_string().getQuery_string();
            String fieldName = query_string.split(":")[0];
            String value = query_string.split(":")[1];

            if( fieldName.equals(logIdField) )
                return FileUtil.readFile(mockLogIdFolder + logIdField + "-" + value + ".csv");
            else
                return FileUtil.readFile(mockRequestIdFolder + requesterIdField + "-" + value + ".csv");
        }
        catch( DataException e )
        {
            return null;
        }
    }
}
