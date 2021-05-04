package com.manjunath.logpuller.representation.request;

import java.util.List;

import lombok.Data;

@Data
public class GraylogList {

    private String logId;
    private String serviceName;
    private String request;
    private String response;
    private List<GrayLogBean> grayLogBeanList;
}
