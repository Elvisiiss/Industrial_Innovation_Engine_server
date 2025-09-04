package com.IIE.Industrial_Innovation_Engine_server.dto;

import lombok.Data;

@Data
public class SearchRequest {
    private Long myId;
    private String keyword;
    private Boolean wannaMe;//是否 不看我的

}
