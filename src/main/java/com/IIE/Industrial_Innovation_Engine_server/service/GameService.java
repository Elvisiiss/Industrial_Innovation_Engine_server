package com.IIE.Industrial_Innovation_Engine_server.service;

import com.IIE.Industrial_Innovation_Engine_server.dto.BaseResponse;
import com.IIE.Industrial_Innovation_Engine_server.entity.Game;


public interface GameService {
    BaseResponse getUserGames(Long id);

    BaseResponse getStats(Long id);

    BaseResponse getPendingGames();

    BaseResponse changeGameStatus(Long id, Long gameId, String status, String description);

    BaseResponse createGame(Long id, Game game);
}
