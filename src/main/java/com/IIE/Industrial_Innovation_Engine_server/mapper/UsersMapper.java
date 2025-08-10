package com.IIE.Industrial_Innovation_Engine_server.mapper;

import com.IIE.Industrial_Innovation_Engine_server.dto.BaseResponse;
import com.IIE.Industrial_Innovation_Engine_server.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UsersMapper {
    List<User> getUsers();

    void changeUserStatus(Long id, Integer status);
}
