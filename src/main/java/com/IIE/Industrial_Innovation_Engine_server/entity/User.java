package com.IIE.Industrial_Innovation_Engine_server.entity;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String user_number;
    private String user_nick_name;
    private String user_name;
    private String user_role;
    private String passwd;
    private String token;
    private String mail;


}
