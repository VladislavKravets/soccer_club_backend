package com.example.soccer_club_backend.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "players")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playerid")
    private Integer playerId;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "birthdate")
    private LocalDate birthDate;

    @Column(name = "position")
    private String position;

    @Column(name = "shirtnumber")
    private Integer shirtNumber;
//
//    @Column(name = "tagid")
//    private Integer tagId;

    @Column(name = "teamid")
    private Integer teamId;

    @Column(name = "active")
    private Boolean active;

    // Getters and setters
}
