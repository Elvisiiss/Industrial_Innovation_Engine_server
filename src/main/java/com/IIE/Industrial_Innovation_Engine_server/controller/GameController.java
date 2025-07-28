package com.IIE.Industrial_Innovation_Engine_server.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.IIE.Industrial_Innovation_Engine_server.dto.GameDTO;
import com.IIE.Industrial_Innovation_Engine_server.dto.ReviewRequest;
import com.IIE.Industrial_Innovation_Engine_server.entity.Game;
import com.IIE.Industrial_Innovation_Engine_server.service.GameService;
import com.IIE.Industrial_Innovation_Engine_server.tools.TokenGenerator;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PutMapping("/{gameId}")
    public ResponseEntity<?> updateGame(
            @RequestHeader("Authorization") String token,
            @PathVariable Long gameId,
            @RequestBody GameDTO gameDTO) {
        String authToken = token.replace("Bearer ", "");
        Long operatorId = TokenGenerator.getUserIdFromToken(authToken);
        Game game = gameService.updateGame(gameId, gameDTO, operatorId);
        return ResponseEntity.ok(game);
    }

    @DeleteMapping("/{gameId}")
    public ResponseEntity<?> deleteGame(
            @RequestHeader("Authorization") String token,
            @PathVariable Long gameId) {
        String authToken = token.replace("Bearer ", "");
        Long operatorId = TokenGenerator.getUserIdFromToken(authToken);
        gameService.deleteGame(gameId, operatorId);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> createGame(
            @RequestHeader("Authorization") String token,
            @RequestBody GameDTO gameDTO) {
        String authToken = token.replace("Bearer ", "");
        Long userId = TokenGenerator.getUserIdFromToken(authToken);
        Game game = gameService.createGame(userId, gameDTO);
        return ResponseEntity.ok(game);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserGames(
            @RequestHeader("Authorization") String token,
            @PathVariable Long userId) {
        List<Game> games = gameService.getUserGames(userId);
        return ResponseEntity.ok(games);
    }

    @PutMapping("/{gameId}/status")
    public ResponseEntity<?> updateGameStatus(
            @RequestHeader("Authorization") String token,
            @PathVariable Long gameId,
            @RequestParam Game.GameStatus status) {
        String authToken = token.replace("Bearer ", "");
        Long operatorId = TokenGenerator.getUserIdFromToken(authToken);
        Game game = gameService.updateGameStatus(gameId, status, operatorId);
        return ResponseEntity.ok(game);
    }

    @GetMapping("/pending")
    public ResponseEntity<?> getPendingReviewGames(
            @RequestHeader("Authorization") String token) {
        List<Game> games = gameService.getPendingReviewGames();
        return ResponseEntity.ok(games);
    }

    @PostMapping("/{gameId}/review")
    public ResponseEntity<?> reviewGame(
            @RequestHeader("Authorization") String token,
            @PathVariable Long gameId,
            @RequestBody ReviewRequest request) {
        String authToken = token.replace("Bearer ", "");
        Long reviewerId = TokenGenerator.getUserIdFromToken(authToken);
        gameService.reviewGame(gameId, request.getAction(), request.getReason(), reviewerId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/reviewed")
    public ResponseEntity<?> getReviewedGames(
            @RequestHeader("Authorization") String token) {
        List<Game> games = gameService.getReviewedGames();
        return ResponseEntity.ok(games);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchGames(
            @RequestHeader("Authorization") String token,
            @RequestParam String keyword) {
        List<Game> games = gameService.searchGames(keyword);
        return ResponseEntity.ok(games);
    }
}
