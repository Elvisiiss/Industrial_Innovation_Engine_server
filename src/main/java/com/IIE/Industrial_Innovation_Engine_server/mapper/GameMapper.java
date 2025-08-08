package com.IIE.Industrial_Innovation_Engine_server.mapper;

import com.IIE.Industrial_Innovation_Engine_server.entity.Game;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface GameMapper {
    List<Game> getUserGames(Long id);

    List<Game> getPendingGames();

    boolean did_it_belong_me(Long id, Long gameId);

    String getGameStatus(Long gameId);

    void changeGameStatus(Long gameId, String status, String description);
}
