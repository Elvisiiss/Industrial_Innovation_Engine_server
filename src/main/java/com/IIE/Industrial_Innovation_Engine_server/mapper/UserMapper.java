package com.IIE.Industrial_Innovation_Engine_server.mapper;

import com.IIE.Industrial_Innovation_Engine_server.dto.UserDTO;
import com.IIE.Industrial_Innovation_Engine_server.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTO(User user);
    User toEntity(UserDTO userDTO);

    void updateUserFromDTO(UserDTO userDTO, @MappingTarget User user);
}
