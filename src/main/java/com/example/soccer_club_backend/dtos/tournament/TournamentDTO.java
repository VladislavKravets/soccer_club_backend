package com.example.soccer_club_backend.dtos.tournament;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TournamentDTO {
//    private Integer tournamentId;
    private String tournamentName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String organizer;
    private String tagName;
//    private String urlPhoto;
}
