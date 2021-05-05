package com.manjunath.logpuller.representation.response;

import com.manjunath.logpuller.representation.request.ServiceLogNode;

import lombok.Data;

@Data
public class ServiceLogNodeResponse {

    private String logId;
    private String serviceName;
    private ServiceLogNode nodes;
}
