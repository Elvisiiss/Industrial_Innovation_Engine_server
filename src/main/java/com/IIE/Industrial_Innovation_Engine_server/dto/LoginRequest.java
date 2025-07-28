package com.IIE.Industrial_Innovation_Engine_server.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String msg;
    private Integer status;
    private String user_number;
    private String e_mail;
    private String passwd;
    private String token;
    private Long user_id;
}
