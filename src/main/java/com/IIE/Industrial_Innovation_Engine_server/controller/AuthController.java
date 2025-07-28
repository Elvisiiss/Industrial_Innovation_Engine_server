package com.IIE.Industrial_Innovation_Engine_server.controller;

import com.IIE.Industrial_Innovation_Engine_server.dto.*;
import com.IIE.Industrial_Innovation_Engine_server.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // 实际应用中应该限制跨域来源
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * 登录 - 使用学号和密码
     */
    @PostMapping("/login/password")
    public LoginResponse loginWithPassword(@RequestBody LoginRequest request) {
        return authService.loginWithPassword(request);
    }

}
