package com.manjunath.logpuller.service.log;

import com.manjunath.logpuller.exceptions.DataException;
import com.manjunath.logpuller.representation.request.ServiceLogNode;

public interface LogService {

    ServiceLogNode getLogs( String logId ) throws DataException;
}
