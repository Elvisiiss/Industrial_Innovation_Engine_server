package com.IIE.Industrial_Innovation_Engine_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {
    private String msg;
    private String code;
    private Object data;

    public static BaseResponse success(String msg,Object data) {
        return new BaseResponse(msg,"Success",data);
    }

    public static BaseResponse error(String msg) {
        return new BaseResponse(msg, "Error",null);
    }
}
