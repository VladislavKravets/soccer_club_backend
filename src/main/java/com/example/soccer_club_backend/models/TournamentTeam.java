package com.example.soccer_club_backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tournamentteams")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TournamentTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tournamentteamid")
    private Integer tournamentTeamId;

    @Column(name = "tournamentid")
    private Integer tournamentId;

    @Column(name = "teamid")
    private Integer teamId;
}
