package com.IIE.Industrial_Innovation_Engine_server.service;

import com.IIE.Industrial_Innovation_Engine_server.dto.BaseResponse;


public interface GameService {
    BaseResponse getUserGames(Long id);

    BaseResponse getStats(Long id);
}
