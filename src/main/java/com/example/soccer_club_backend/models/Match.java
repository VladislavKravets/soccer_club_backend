package com.example.soccer_club_backend.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "matches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matchid")
    private Integer matchId;

    @Column(name = "tournamentid")
    private Integer tournamentId;

    @Column(name = "matchdate")
    private LocalDate matchDate;

    @Column(name = "matchtime")
    @DateTimeFormat(pattern = "H:mm")
    private LocalTime matchTime;

    @Column(name = "team1id")
    private Integer team1Id;

    @Column(name = "team2id")
    private Integer team2Id;

    @Column(name = "refereeid")
    private Integer refereeId;

//    @Column(name = "tagid")
//    private Integer tagId;

    @Column(name = "team1totalgoals")
    private Integer team1TotalGoals;

    @Column(name = "team2totalgoals")
    private Integer team2TotalGoals;

    @Column(name = "groupStage")
    private Integer groupStage;
}
