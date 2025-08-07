package com.IIE.Industrial_Innovation_Engine_server.dto;

import lombok.Data;

@Data
public class MyGameStatsResponse {
    private Long totalGamesNumber;
    private Long publicGamesNumber;
    private Long todayGamesWithoutMeViewNumber;
    private Long todayGamesViewNumber;

    public MyGameStatsResponse(Long totalGamesNumber, Long publicGamesNumber, Long todayGamesWithoutMeViewNumber, Long todayGamesViewNumber) {
        this.totalGamesNumber = totalGamesNumber;
        this.publicGamesNumber = publicGamesNumber;
        this.todayGamesWithoutMeViewNumber = todayGamesWithoutMeViewNumber;
        this.todayGamesViewNumber = todayGamesViewNumber;
    }

}
