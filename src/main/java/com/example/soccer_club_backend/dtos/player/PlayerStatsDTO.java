package com.example.soccer_club_backend.dtos.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerStatsDTO {
    private Integer playerId;
    private String playerName;
    private Long totalMatchesPlayed;
    private Long goals;
    private Long penaltyGoals;
    private BigDecimal averageGoalsPerGame;
    private Long yellowCards;
    private Long redCards;

    // Конструктор, геттери та сеттери
}
