package com.example.soccer_club_backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "tournamenttablehistory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TournamentTableHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "historyid")
    private Integer historyId;

    @Column(name = "tournamentid")
    private Integer tournamentId;

    @Column(name = "teamid")
    private Integer teamId;

    @Column(name = "pointsdelta")
    private Integer pointsDelta;

    @Column(name = "matchid")
    private Integer matchId;

    @Column(name = "timestamp")
    private LocalDate timestamp;
}
