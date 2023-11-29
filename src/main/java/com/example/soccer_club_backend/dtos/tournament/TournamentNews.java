package com.example.soccer_club_backend.dtos.tournament;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class TournamentNews {
//    private int tournamentId;
//    private String tournamentName;
    private int newsId;
    private String title;
    private String content;
    private Date publicationDate;
    private String author;
    private String photoPath;
}
