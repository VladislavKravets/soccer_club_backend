package com.example.soccer_club_backend.dtos.tournament;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TournamentGetInfo {
    private Integer tournamentId;
    private String tournamentName;
    private String photoUrl;
    private Date startDate;
    private Date endDate;
    private long countTeam;
    private String status;
    private String tagName;
}
