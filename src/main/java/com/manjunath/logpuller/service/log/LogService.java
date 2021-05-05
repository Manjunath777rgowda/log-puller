package com.manjunath.logpuller.service.log;

import com.manjunath.logpuller.exceptions.DataException;
import com.manjunath.logpuller.representation.response.ServiceLogNodeResponse;

public interface LogService {

    ServiceLogNodeResponse getLogs( String logId ) throws DataException;
}
