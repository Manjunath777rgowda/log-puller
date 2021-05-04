package com.manjunath.logpuller.representation.request;

import lombok.Data;

@Data
public class GrayLogOutput {

    private GraylogList owner;
    private GraylogList parent;
    private GraylogList children;
}
