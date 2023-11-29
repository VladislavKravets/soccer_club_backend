package com.example.soccer_club_backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "footballteams")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FootballTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teamid")
    private Integer teamId;

    @Column(name = "teamname")
    private String teamName;

    @Column(name = "coach")
    private String coach;

    @Column(name = "district")
    private String district;

    @ManyToOne
    private Tag tag;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "photo_id")
    private Photo photo;
}
