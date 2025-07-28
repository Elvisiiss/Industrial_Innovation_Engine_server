package com.IIE.Industrial_Innovation_Engine_server.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TokenMapper {
    void updateToken(Long id, String token);
}
