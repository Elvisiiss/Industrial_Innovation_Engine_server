package com.IIE.Industrial_Innovation_Engine_server.service;

import com.IIE.Industrial_Innovation_Engine_server.dto.BaseResponse;


public interface GameService {
    BaseResponse getUserGames(Long id);

    BaseResponse getStats(Long id);

    BaseResponse getPendingGames();

    BaseResponse changeGameStatus(Long id, Long gameId, String status, String description);
}
