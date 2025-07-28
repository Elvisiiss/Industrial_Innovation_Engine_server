package com.IIE.Industrial_Innovation_Engine_server.service;

import java.util.List;

import com.IIE.Industrial_Innovation_Engine_server.dto.GameDTO;
import com.IIE.Industrial_Innovation_Engine_server.entity.Game;

public interface GameService {
    Game createGame(Long userId, GameDTO gameDTO);
    List<Game> getUserGames(Long userId);
    Game updateGameStatus(Long gameId, Game.GameStatus status, Long operatorId);
    List<Game> getPendingReviewGames();
    Game updateGame(Long gameId, GameDTO gameDTO, Long operatorId);
    void deleteGame(Long gameId, Long operatorId);
    void reviewGame(Long gameId, String action, String reason, Long reviewerId);
    List<Game> getReviewedGames();
    List<Game> searchGames(String keyword);
}
