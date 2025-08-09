package com.IIE.Industrial_Innovation_Engine_server.controller;

import com.IIE.Industrial_Innovation_Engine_server.dto.*;
import com.IIE.Industrial_Innovation_Engine_server.entity.Game;
import com.IIE.Industrial_Innovation_Engine_server.mapper.TokenMapper;
import com.IIE.Industrial_Innovation_Engine_server.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    //我的游戏 - 增删改查
    /**
    *  增加一个我的游戏
    * */
    @PostMapping("")
    public BaseResponse createGame(@RequestHeader String Token,@RequestBody Game game) {
        Long id = tokenMapper.getIdByToken(Token);
        if(id==null) {
            return BaseResponse.error("Token错误");
        }
        return gameService.createGame(game);
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
     * 获取待审核游戏
     */
    @GetMapping("/pending")
    public BaseResponse getPendingGames(@RequestHeader String Token) {
        Long id = tokenMapper.getIdByToken(Token);
        if(id==null) {
            return BaseResponse.error("Token错误");
        }
        return gameService.getPendingGames();
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

    /**
     * 修改我的游戏的状态
     * */
    @PatchMapping("/{gameId}/status")
    public BaseResponse changeGameStatus(
            @RequestHeader String Token,
            @PathVariable Long gameId,
            @RequestBody Map<String, String> requestBody
    ) {
        String status = requestBody.get("status");
        String description = requestBody.get("description");
        Long id = tokenMapper.getIdByToken(Token);
        if(id==null) {
            return BaseResponse.error("Token错误");
        }
        return gameService.changeGameStatus(id,gameId,status, description);
    }
}
