package com.example.soccer_club_backend.dtos.footballTeam;

import lombok.*;

import java.sql.Time;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MatchInfo {
    private String date;
    private Time matchTime;
    private Integer team1_link;
    private Integer team2_link;
    private Integer match_link;
    private String nameTeam1;
    private String nameTeam2;
    private String score;
    private String win;
}
