package com.IIE.Industrial_Innovation_Engine_server.service.impl;

import com.IIE.Industrial_Innovation_Engine_server.dto.BaseResponse;
import com.IIE.Industrial_Innovation_Engine_server.dto.MyGameStatsResponse;
import com.IIE.Industrial_Innovation_Engine_server.entity.Game;
import com.IIE.Industrial_Innovation_Engine_server.entity.Tag;
import com.IIE.Industrial_Innovation_Engine_server.mapper.GameMapper;
import com.IIE.Industrial_Innovation_Engine_server.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public BaseResponse createGame(Long userId, Game game) {
        try {
            // 设置游戏归属用户
            game.setGameBelong(userId);

            // 设置默认审核描述（如果需要）
            if (game.getExamineDescription() == null) {
                game.setExamineDescription("-");
            }

            // 插入游戏记录
            gameMapper.createGame(game);
            Long gameId = game.getId(); // 获取自增ID

            // 处理标签
            List<Tag> tags = game.getTags();
            if (tags != null && !tags.isEmpty()) {
                for (Tag tag : tags) {
                    processTag(gameId, tag);
                }
            }

            return BaseResponse.success("游戏创建成功", game);
        } catch (Exception e) {
            throw new RuntimeException("创建游戏失败: " + e.getMessage());
        }
    }

    private void processTag(Long gameId, Tag tag) {
        if (tag.getId() == null) {
            // 新标签：检查是否已存在同名标签
            Tag existingTag = gameMapper.getTagByName(tag.getTagName());
            if (existingTag != null) {
                // 使用已存在的标签
                gameMapper.insertGameTag(gameId, existingTag.getId());
            } else {
                // 插入新标签
                gameMapper.insertTag(tag);
                gameMapper.insertGameTag(gameId, tag.getId());
            }
        } else {
            // 已有标签：验证ID是否有效
            if (!gameMapper.checkTagExists(tag.getId())) {
                throw new RuntimeException("Tag问题: 标签ID不存在");
            }
            // 关联游戏和标签
            gameMapper.insertGameTag(gameId, tag.getId());
        }
    }

    private boolean canToThere(String oStatus, String status){
        if(oStatus.equals("PRIVATE") && status.equals("UNAPPROVED")) return true;
        if(oStatus.equals("UNAPPROVED") && status.equals("PUBLIC")) return true;
        if(oStatus.equals("UNAPPROVED") && status.equals("APPROVED")) return true;
        if(oStatus.equals("APPROVED") && status.equals("UNAPPROVED")) return true;
        if(oStatus.equals("PUBLIC") && status.equals("PRIVATE")) return true;
        if(oStatus.equals("UNAPPROVED") && status.equals("PRIVATE")) return true;
        return false;
    }
}
