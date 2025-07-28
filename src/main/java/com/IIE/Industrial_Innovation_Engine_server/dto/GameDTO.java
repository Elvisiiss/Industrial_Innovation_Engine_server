package com.IIE.Industrial_Innovation_Engine_server.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.IIE.Industrial_Innovation_Engine_server.entity.Game;

import lombok.Data;

@Data
public class GameDTO {
    private Long id;
    private String title;
    private String url;
    private String description;
    private Game.GameStatus status;
    private Long creatorId;
    private String creatorName;
    private List<String> tags;
    private LocalDateTime createdAt;
    private LocalDateTime reviewedAt;
    private Long viewCount;
}
