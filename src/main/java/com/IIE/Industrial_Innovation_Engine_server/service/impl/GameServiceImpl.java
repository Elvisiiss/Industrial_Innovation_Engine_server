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

    @Override
    public BaseResponse getPendingGames() {
        return BaseResponse.success("获取成功",gameMapper.getPendingGames());
    }

    @Override
    public BaseResponse changeGameStatus(Long id, Long gameId, String status, String description) {
        if(!gameMapper.did_it_belong_me(id,gameId)){
            return BaseResponse.error("？这游戏不属于你");
        }
        String oStatus = gameMapper.getGameStatus(gameId);
        if(!canToThere(oStatus,status)){
            return BaseResponse.error("？无法达到的游戏状态");
        }
        gameMapper.changeGameStatus(gameId,status,description);
        return BaseResponse.success("修改到"+status+"状态成功",null);
    }

    private boolean canToThere(String oStatus, String status){
        if(oStatus.equals("PRIVATE") && status.equals("UNAPPROVED")) return true;
        if(oStatus.equals("UNAPPROVED") && status.equals("PUBLIC")) return true;
        if(oStatus.equals("UNAPPROVED") && status.equals("APPROVED")) return true;
        if(oStatus.equals("APPROVED") && status.equals("UNAPPROVED")) return true;
        if(oStatus.equals("PUBLIC") && status.equals("PRIVATE")) return true;
        return false;
    }
}
