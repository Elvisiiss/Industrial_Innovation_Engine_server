package com.IIE.Industrial_Innovation_Engine_server.service.impl;

import com.IIE.Industrial_Innovation_Engine_server.dto.*;
import com.IIE.Industrial_Innovation_Engine_server.mapper.AuthMapper;
import com.IIE.Industrial_Innovation_Engine_server.mapper.TokenMapper;
import com.IIE.Industrial_Innovation_Engine_server.entity.User;
import com.IIE.Industrial_Innovation_Engine_server.service.AuthService;
import com.IIE.Industrial_Innovation_Engine_server.tools.PasswordEncryptor;
import com.IIE.Industrial_Innovation_Engine_server.tools.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthMapper authMapper;
    private final TokenMapper tokenMapper;

    @Autowired
    public AuthServiceImpl(AuthMapper authMapper,
                           TokenMapper tokenMapper) {
        this.authMapper = authMapper;
        this.tokenMapper = tokenMapper;

    }

    @Override
    public LoginResponse loginWithPassword(LoginRequest request) {
        String password = PasswordEncryptor.encryptPassword(request.getPasswd());
        User user = null;

        String userNumber = request.getUser_number();
        Optional<User> optionalUser = Optional.ofNullable(authMapper.findByUserNumber(userNumber));
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        }

        // 验证用户和密码
        if (user == null || !user.getPasswd().equals(password)) {
            return LoginResponse.error("账户或密码错误");
        }
        String user_token = TokenGenerator.generateToken();
        tokenMapper.updateToken(user.getId(),user_token);
        user.setToken(user_token);
        return LoginResponse.success(user.getId(),user.getMail(), user.getUser_name(), user.getUser_role(),user_token);
    }
}
