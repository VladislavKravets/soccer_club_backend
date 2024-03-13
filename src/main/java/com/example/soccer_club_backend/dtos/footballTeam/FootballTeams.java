package com.example.soccer_club_backend.dtos.footballTeam;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FootballTeams {
    private Integer teamId;
    private String teamName;
    private String coach;
    private String district;
    private String tagName;
    private String photoUrl;
}
