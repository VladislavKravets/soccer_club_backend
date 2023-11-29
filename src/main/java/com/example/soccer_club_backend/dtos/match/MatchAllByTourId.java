package com.example.soccer_club_backend.dtos.match;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class MatchAllByTourId {
    private Integer matchId;
    private Date matchDate;
    private String matchtime;
    private String dayOfWeek;
    private Integer team1TotalGoals;
    private Integer team2TotalGoals;
    private String teamName1;
    private int teamId1;
    private String teamName2;
    private int teamId2;
    private Integer groupStage;
}
