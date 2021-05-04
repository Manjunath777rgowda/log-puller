/* Copyright(C) 2020-21. Nuvepro Pvt. Ltd. All rights reserved */

package com.manjunath.logpuller.restclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.manjunath.logpuller.representation.request.GetLogRequestToGrayLog;

@FeignClient( url = "${graylog-server-url}", name = "graylog-rest-client" )
public interface GraylogClient {

    @PostMapping( "/views/search/messages" )
    String getLogs( @RequestHeader( "Authorization" ) String token,
            @RequestHeader( "X-Requested-By" ) String requestedBy,
            @RequestBody GetLogRequestToGrayLog getLogRequestToGrayLog );
}
