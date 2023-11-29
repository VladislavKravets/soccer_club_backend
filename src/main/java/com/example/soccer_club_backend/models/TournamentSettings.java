package com.example.soccer_club_backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tournamentsettings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TournamentSettings {
    @Id
    @Column(name = "tournamentid")
    private Integer tournamentId;

    @Column(name = "pointsforwin")
    private Integer pointsForWin;

    @Column(name = "pointsfordraw")
    private Integer pointsForDraw;
}
