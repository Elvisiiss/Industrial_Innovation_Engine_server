package com.IIE.Industrial_Innovation_Engine_server.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.IIE.Industrial_Innovation_Engine_server.dto.GameDTO;
import com.IIE.Industrial_Innovation_Engine_server.entity.Game;
import com.IIE.Industrial_Innovation_Engine_server.entity.User;
import com.IIE.Industrial_Innovation_Engine_server.repository.GameRepository;
import com.IIE.Industrial_Innovation_Engine_server.repository.UserRepository;
import com.IIE.Industrial_Innovation_Engine_server.service.GameService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public GameServiceImpl(GameRepository gameRepository, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Game createGame(Long userId, GameDTO gameDTO) {
        User creator = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Game game = new Game();
        game.setTitle(gameDTO.getTitle());
        game.setUrl(gameDTO.getUrl());
        game.setDescription(gameDTO.getDescription());
        game.setStatus(gameDTO.getStatus());
        game.setCreator(creator);
        game.setTags(String.join(",", gameDTO.getTags()));

        return gameRepository.save(game);
    }

    @Override
    public List<Game> getUserGames(Long userId) {
        return gameRepository.findByCreatorId(userId);
    }

    @Override
    public Game updateGameStatus(Long gameId, Game.GameStatus status, Long operatorId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        // 验证操作权限
        validateOperationPermission(game, operatorId);

        game.setStatus(status);
        if (status == Game.GameStatus.PUBLIC || status == Game.GameStatus.PRIVATE) {
            game.setReviewedAt(java.time.LocalDateTime.now());
            game.setReviewer(userRepository.getReferenceById(operatorId));
        }

        return gameRepository.save(game);
    }

    @Override
    public List<Game> getPendingReviewGames() {
        return gameRepository.findByStatus(Game.GameStatus.PENDING_REVIEW);
    }

    @Override
    public Game updateGame(Long gameId, GameDTO gameDTO, Long operatorId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));
        
        validateOperationPermission(game, operatorId);
        
        game.setTitle(gameDTO.getTitle());
        game.setDescription(gameDTO.getDescription());
        game.setUrl(gameDTO.getUrl());
        game.setStatus(gameDTO.getStatus());
        game.setTags(String.join(",", gameDTO.getTags()));
        
        return gameRepository.save(game);
    }

    @Override
    public void deleteGame(Long gameId, Long operatorId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));
        
        validateOperationPermission(game, operatorId);
        
        gameRepository.delete(game);
    }

    @Override
    public void reviewGame(Long gameId, String action, String reason, Long reviewerId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));
        
        User reviewer = userRepository.findById(reviewerId)
                .orElseThrow(() -> new RuntimeException("Reviewer not found"));
        
        if (!"管理员".equals(reviewer.getUserRole()) && !"教师".equals(reviewer.getUserRole())) {
            throw new RuntimeException("只有管理员或教师可以审核游戏");
        }

        Game.GameStatus newStatus = "approve".equals(action) ? 
            Game.GameStatus.PUBLIC : Game.GameStatus.PRIVATE;
        
        game.setStatus(newStatus);
        game.setReviewedAt(java.time.LocalDateTime.now());
        game.setReviewer(reviewer);
        game.setReviewComment(reason);
        
        gameRepository.save(game);
    }

    @Override
    public List<Game> getReviewedGames() {
        return gameRepository.findByStatusIn(
            List.of(Game.GameStatus.PUBLIC, Game.GameStatus.PRIVATE));
    }

    @Override
    public List<Game> searchGames(String keyword) {
        String searchPattern = "%" + keyword + "%";
        return gameRepository.findByTitleLikeOrDescriptionLike(
            searchPattern, searchPattern);
    }

    private void validateOperationPermission(Game game, Long operatorId) {
        User operator = userRepository.findById(operatorId)
                .orElseThrow(() -> new RuntimeException("Operator not found"));

        if ("管理员".equals(operator.getUserRole())) {
            return; // 管理员有全部权限
        }

        if ("教师".equals(operator.getUserRole())) {
            if (game.getStatus() != Game.GameStatus.PENDING_REVIEW) {
                throw new RuntimeException("教师只能审核待审核的游戏");
            }
            return;
        }

        // 学生只能操作自己的游戏
        if (!game.getCreator().getId().equals(operatorId)) {
            throw new RuntimeException("无权操作他人的游戏");
        }
    }
}
