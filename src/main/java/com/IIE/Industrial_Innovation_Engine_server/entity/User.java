package com.IIE.Industrial_Innovation_Engine_server.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")  // 指定对应的数据库表名
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_number")  // 指定列名
    private String userNumber;

    @Column(name = "user_nick_name")
    private String userNickName;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_role")
    private String userRole;

    @Column(name = "passwd")
    private String passwd;

    @Column(name = "token")
    private String token;

    @Column(name = "mail")
    private String mail;

    @Column(name = "status")
    private String status;

}
