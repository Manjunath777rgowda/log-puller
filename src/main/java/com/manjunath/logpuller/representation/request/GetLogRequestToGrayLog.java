package com.manjunath.logpuller.representation.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetLogRequestToGrayLog {

    private TimeRange timerange;
    private List<String> fields_in_order;
    private Query_string query_string;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TimeRange {

        private int range;
        private String type;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Query_string {

        private String type;
        private String query_string;
    }

}
