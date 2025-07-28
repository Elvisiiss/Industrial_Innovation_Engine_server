package com.IIE.Industrial_Innovation_Engine_server.service;

import com.IIE.Industrial_Innovation_Engine_server.dto.*;


public interface AuthService {

    // 登录 - 使用学号和密码
    LoginResponse loginWithPassword(LoginRequest request);
}
