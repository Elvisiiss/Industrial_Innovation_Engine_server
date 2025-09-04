package com.IIE.Industrial_Innovation_Engine_server.service;

import com.IIE.Industrial_Innovation_Engine_server.dto.BaseResponse;
import com.IIE.Industrial_Innovation_Engine_server.dto.SearchRequest;
import com.IIE.Industrial_Innovation_Engine_server.entity.Game;


public interface GameService {
    BaseResponse getUserGames(Long id);

    BaseResponse getStats(Long id);

    BaseResponse getPendingGames();

    BaseResponse changeGameStatus(Long id, Long gameId, String status, String description);

    BaseResponse createGame(Long id, Game game);

    BaseResponse reviewGame(Long id, Long gameId, String status, String examineDescription);

    BaseResponse getGameById(Long id, Long gameId);

    BaseResponse updateGame(Long id, Long gameId, Game game);

    BaseResponse searchGames(SearchRequest searchRequest);

    BaseResponse deleteGame(Long id, Long gameId);
}
