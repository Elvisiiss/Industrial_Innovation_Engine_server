package com.IIE.Industrial_Innovation_Engine_server.controller;

import com.IIE.Industrial_Innovation_Engine_server.dto.*;
import com.IIE.Industrial_Innovation_Engine_server.mapper.TokenMapper;
import com.IIE.Industrial_Innovation_Engine_server.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games")
@CrossOrigin(origins = "*") // 实际应用中应该限制跨域来源
public class GameController {

    private final GameService gameService;
    private final TokenMapper tokenMapper;

    @Autowired
    public GameController(GameService gameService, TokenMapper tokenMapper) {
        this.gameService = gameService;
        this.tokenMapper = tokenMapper;
    }

    /**
     * 获取我的所有游戏
     */
    @GetMapping("/user/{userId}")
    public BaseResponse getUserGames(@RequestHeader String Token,@PathVariable Integer userId) {
        Long id = tokenMapper.getIdByToken(Token);
        if(id==null || !id.toString().equals(userId.toString())) {
            return BaseResponse.error("Token错误");
        }
        return gameService.getUserGames(id);
    }

    /**
    * 获取我的游戏的状态
    * */
    @GetMapping("/stats")
    public BaseResponse getStats(@RequestHeader String Token) {
        Long id = tokenMapper.getIdByToken(Token);
        if(id==null) {
            return BaseResponse.error("Token错误");
        }
        return gameService.getStats(id);
    }
}
