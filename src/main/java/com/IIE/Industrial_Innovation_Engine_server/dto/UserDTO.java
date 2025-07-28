package com.IIE.Industrial_Innovation_Engine_server.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String userNumber;
    private String userName;
    private String password;
    private String role;
    private String status;
    private String email;
    private String token;
}
