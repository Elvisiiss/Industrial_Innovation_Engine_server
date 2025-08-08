package com.IIE.Industrial_Innovation_Engine_server.entity;

import lombok.Data;

import java.util.List;

@Data
public class Game {
    private Long id;

    private String gameName;

    private String gameDescription;

    private String gameUrl;

    private String gamePicture;

    private Long gameBelong;

    private String status;

    private String examineDescription;

    private List<Tag> tags;


}
