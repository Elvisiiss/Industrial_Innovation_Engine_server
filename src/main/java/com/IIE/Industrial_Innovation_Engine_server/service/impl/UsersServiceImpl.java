package com.IIE.Industrial_Innovation_Engine_server.service.impl;

import com.IIE.Industrial_Innovation_Engine_server.dto.BaseResponse;
import com.IIE.Industrial_Innovation_Engine_server.mapper.UsersMapper;
import com.IIE.Industrial_Innovation_Engine_server.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersMapper usersMapper;

    @Autowired
    public UsersServiceImpl(UsersMapper usersMapper) {
        this.usersMapper = usersMapper;
    }

    @Override
    public BaseResponse getUsers() {
        return BaseResponse.success("返回成功",usersMapper.getUsers());
    }

    @Override
    public BaseResponse changeUserStatus(Long id, Long userId, Integer status) {
        usersMapper.changeUserStatus(id,status);
        return BaseResponse.success("用户"+userId+"修改了用户"+id+"的状态，修改为“"+status+"”",status);
    }


}
