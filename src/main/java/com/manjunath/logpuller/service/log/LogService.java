package com.manjunath.logpuller.service.log;

import com.manjunath.logpuller.exceptions.DataException;
import com.manjunath.logpuller.representation.request.GrayLogOutput;

public interface LogService {

    GrayLogOutput getLogs(String logId ) throws DataException;
}
