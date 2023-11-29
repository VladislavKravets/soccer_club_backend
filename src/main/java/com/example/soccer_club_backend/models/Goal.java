package com.example.soccer_club_backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "goals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goalid")
    private Integer goalId;

    @Column(name = "playerid")
    private Integer playerId;

    @Column(name = "matchid")
    private Integer matchId;

    @Column(name = "minute")
    private Integer minute;

    @Column(name = "description")
    private String description;

    // Getters and setters
}
