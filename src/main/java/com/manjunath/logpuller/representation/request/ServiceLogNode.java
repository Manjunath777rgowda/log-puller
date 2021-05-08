package com.manjunath.logpuller.representation.request;

import java.util.List;

import lombok.Data;

@Data
public class ServiceLogNode {

    private String logId;
    private String serviceName;
    private String requestBody;
    private String requestParams;
    private String responseBody;
    private String responseCode;
    private String responseStatus;
    private String endpoint;
    private String userId;
    private String username;
    private String tenant;
    private String environment;
    private String requesterId;
    private boolean isDefault;
    private List<GrayLogBean> grayLogBeanList;
    private List<ServiceLogNode> children;
}
