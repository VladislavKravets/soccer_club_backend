package com.example.soccer_club_backend.dtos.match;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatchDTO {
    private Integer tournamentId;
    private LocalDate matchDate;
    @DateTimeFormat(pattern = "H:mm")
    private LocalTime matchTime;
    private Integer team1Id;
    private Integer team2Id;
    private Integer refereeId;
    //    private Integer tagId;
    private Integer team1TotalGoals;
    private Integer team2TotalGoals;
    private Integer groupStage;
}
