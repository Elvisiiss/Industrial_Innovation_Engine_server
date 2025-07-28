package com.IIE.Industrial_Innovation_Engine_server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.IIE.Industrial_Innovation_Engine_server.entity.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByCreatorId(Long creatorId);
    List<Game> findByStatus(Game.GameStatus status);
    List<Game> findByStatusIn(List<Game.GameStatus> statuses);
    List<Game> findByTitleLikeOrDescriptionLike(String titlePattern, String descPattern);
}
