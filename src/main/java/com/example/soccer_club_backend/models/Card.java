package com.example.soccer_club_backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cardid")
    private Integer cardId;

    @Column(name = "matchid")
    private Integer matchId;

    @Column(name = "playerid")
    private Integer playerId;

    @Column(name = "refereeid")
    private Integer refereeId;

    @Column(name = "minute")
    private Integer minute;

    @Column(name = "cardtype")
    private String cardType;
}
