package com.IIE.Industrial_Innovation_Engine_server.entity;

import lombok.Data;

@Data
public class User {

    private Long id;

    private String userNumber;

    private String userNickName;

    private String userName;

    private String userRole;

    private String passwd;

    private String token;

    private String mail;

    private String status;

}
