package com.manjunath.logpuller.representation.request;

import java.util.List;

import lombok.Data;

@Data
public class ServiceLogNode {

    private String logId;
    private String serviceName;
    private String request;
    private String response;
    private String statusCode;
    private String statusResponse;
    private boolean isDefault;
    private List<GrayLogBean> grayLogBeanList;
    private List<ServiceLogNode> children;
}
