package com.example.soccer_club_backend.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tournamenttable")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TournamentTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tableid")
    private Integer tableId;

    @Column(name = "tournamentid")
    private Integer tournamentId;

    @Column(name = "teamid")
    private Integer teamId;

    @Column(name = "points")
    private Integer points;

    // Getters and setters
}

