package com.IIE.Industrial_Innovation_Engine_server.mapper;

import com.IIE.Industrial_Innovation_Engine_server.entity.Game;
import com.IIE.Industrial_Innovation_Engine_server.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface GameMapper {
    List<Game> getUserGames(Long id);

    List<Game> getPendingGames();

    boolean did_it_belong_me(Long id, Long gameId);

    String getGameStatus(Long gameId);

    void changeGameStatus(Long gameId, String status, String description);

    void createGame(Game game);
    Tag getTagByName(String tagName);
    void insertTag(Tag tag);
    boolean checkTagExists(Long tagId);
    void insertGameTag(@Param("gameId") Long gameId, @Param("tagId") Long tagId);

    void reviewGame(Long gameId, String status, String examineDescription);

    Game getGameById(Long id, Long gameId);
}
