package com.example.soccer_club_backend.dtos.tournament;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class TournamentTeamStatsDTO {
    private Integer teamId;
    private String teamName;
    private Long totalMatchesPlayed;
    private Long wins;
    private Long draws;
    private Long losses;
    private Long goalsScored;
    private Long goalsConceded;
    private Integer points;
    private Map<String, Integer[]> matchIds;
}

