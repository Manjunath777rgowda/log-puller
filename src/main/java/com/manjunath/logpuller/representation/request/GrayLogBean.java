package com.manjunath.logpuller.representation.request;

import lombok.Data;

@Data
public class GrayLogBean {

    private String server;
    private String requesterId;
    private String loggerLevel;
    private String StackTrace;
    private String ip;
    private String requestMethod;
    private String className;
    private String simpleClassName;
    private String source;
    private String message;
    private String userId;
    private String endpoint;
    private String request;
    private String response;
    private String responseCode;
    private String environment;
    private String server_fqdn;
    private String full_message;
    private String service;
    private String logId;
    private String facility;
    private String inbound_json;
    private String tenant;
    private String timestamp;
    private String username;
}
