package com.example.soccer_club_backend.dtos.footballTeam;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class FootballTeamInfo {
    private String TeamName;
    private Integer numberMathPlayed;
    private Integer wins;
    private Integer goals;
    private Integer playedTour;
    private String photoUrl;
    private Map<String, List<Integer>> matchIds; // Change Integer[] to List<Integer>
}
