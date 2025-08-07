package com.IIE.Industrial_Innovation_Engine_server.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Game {
    private Long id;

    private String gameName;

    private String gameDescription;

    private String gameUrl;

    private String gamePicture;

    private String status;

    private Long gameBelong;

    private List<Tag> tags;


}
