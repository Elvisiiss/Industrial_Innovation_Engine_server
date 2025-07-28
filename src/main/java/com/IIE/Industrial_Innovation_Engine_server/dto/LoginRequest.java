package com.IIE.Industrial_Innovation_Engine_server.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String user_number;
    private String passwd;
}
