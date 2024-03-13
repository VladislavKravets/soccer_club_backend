package com.example.soccer_club_backend.dtos.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetIDMatchDTO {
    private String tournamentName;
    private int tournamentId;
    private Date matchDate;
    private Time matchTime;
    private String matchDay;
    private String team1Name;
    private String team2Name;
    private Integer team1Id;
    private Integer team2Id;
    private String photoTeam1;
    private String photoTeam2;
    private String score;
}
