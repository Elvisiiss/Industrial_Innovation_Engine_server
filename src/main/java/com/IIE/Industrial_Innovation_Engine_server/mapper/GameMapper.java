package com.IIE.Industrial_Innovation_Engine_server.mapper;

import com.IIE.Industrial_Innovation_Engine_server.entity.Game;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface GameMapper {
    List<Game> getUserGames(Long id);
}
