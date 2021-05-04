/* Copyright(C) 2020-21. Nuvepro Pvt. Ltd. All rights reserved. */

package com.manjunath.logpuller.exceptions;

import org.springframework.http.HttpStatus;

import com.manjunath.logpuller.constants.ErrorMessages;
import com.manjunath.logpuller.utils.FileUtil;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DataException extends Exception {

    private final String errorMessage;
    private final HttpStatus httpStatus;

    public DataException( Exception e )
    {
        super(e);
        this.errorMessage = ErrorMessages.SOMETHING_WENT_WRONG;
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        FileUtil.createStackTraceFile(e);
    }

}
