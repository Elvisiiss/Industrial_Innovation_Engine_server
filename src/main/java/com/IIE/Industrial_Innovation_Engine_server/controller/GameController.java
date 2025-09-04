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
        return gameService.createGame(id, game);
    }

    /**
     *  删除一个我的游戏
     * */
    @DeleteMapping("/{gameId}")
    public BaseResponse deleteGame(
            @RequestHeader String Token,
            @PathVariable Long gameId
    ) {
        Long id = tokenMapper.getIdByToken(Token);
        if(id==null) {
            return BaseResponse.error("Token错误");
        }
        return gameService.deleteGame(id, gameId);
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

    /**
     * 审核
     * */
    @PostMapping("/{gameId}/review")
    public BaseResponse reviewGame(
            @RequestHeader String Token,
            @PathVariable Long gameId,
            @RequestBody Map<String, String> requestBody
    ) {
        String status = requestBody.get("status");
        String examineDescription = requestBody.get("examineDescription");
        Long id = tokenMapper.getIdByToken(Token);
        if(id==null) {
            return BaseResponse.error("Token错误");
        }
        return gameService.reviewGame(id,gameId,status, examineDescription);
    }

    /**
     * 获取某个游戏的内容
     * */
    @GetMapping("/get/{gameId}")
    public BaseResponse getGameById(
            @RequestHeader String Token,
            @PathVariable Long gameId
    ) {
        Long id = tokenMapper.getIdByToken(Token);
        if(id==null) {
            return BaseResponse.error("Token错误");
        }
        return gameService.getGameById(id,gameId);
    }


    /**
     * 修改游戏内容
     * */
    @PutMapping("/{gameId}")
    public BaseResponse updateGame(
            @RequestHeader String Token,
            @PathVariable Long gameId,
            @RequestBody Game game
    ) {
        Long id = tokenMapper.getIdByToken(Token);
        if(id==null) {
            return BaseResponse.error("Token错误");
        }
        return gameService.updateGame(id,gameId,game);
    }

    /**
     * 搜索游戏
     * */
    @GetMapping("/search")
    public BaseResponse searchGames(
            @RequestHeader String Token,
            @ModelAttribute SearchRequest searchRequest
    ) {
        Long id = tokenMapper.getIdByToken(Token);
        if(id==null || !id.equals(searchRequest.getMyId())) {
            return BaseResponse.error("Token错误");
        }
        return gameService.searchGames(searchRequest);
    }
}
