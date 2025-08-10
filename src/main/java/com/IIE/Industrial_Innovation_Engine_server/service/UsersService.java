package com.IIE.Industrial_Innovation_Engine_server.service;

import com.IIE.Industrial_Innovation_Engine_server.dto.BaseResponse;

public interface UsersService {
    BaseResponse getUsers();

    BaseResponse changeUserStatus(Long id, Long userId, Integer status);
}
