package com.IIE.Industrial_Innovation_Engine_server.mapper;

import com.IIE.Industrial_Innovation_Engine_server.entity.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface AuthMapper {
    User findByUserNumber(String user_number);

}
