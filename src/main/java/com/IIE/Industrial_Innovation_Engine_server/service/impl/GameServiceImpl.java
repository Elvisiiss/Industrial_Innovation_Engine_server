package com.IIE.Industrial_Innovation_Engine_server.service.impl;

import com.IIE.Industrial_Innovation_Engine_server.dto.BaseResponse;
import com.IIE.Industrial_Innovation_Engine_server.dto.MyGameStatsResponse;
import com.IIE.Industrial_Innovation_Engine_server.entity.Game;
import com.IIE.Industrial_Innovation_Engine_server.mapper.GameMapper;
import com.IIE.Industrial_Innovation_Engine_server.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    private final GameMapper gameMapper;

    @Autowired
    public GameServiceImpl(GameMapper gameMapper) {
        this.gameMapper = gameMapper;
    }

    @Override
    public BaseResponse getUserGames(Long id) {
        List<Game> games = gameMapper.getUserGames(id);
        return BaseResponse.success("获取成功",games);
    }

    @Override
    public BaseResponse getStats(Long id) {
        Long totalGamesNumber=11L;
        Long publicGamesNumber=12L;
        Long todayGamesWithoutMeViewNumber=13L;
        Long todayGamesViewNumber=14L;
        return BaseResponse.success("获取成功",new MyGameStatsResponse(totalGamesNumber,publicGamesNumber,todayGamesWithoutMeViewNumber,todayGamesViewNumber));

    }
}
