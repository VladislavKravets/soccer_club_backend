package com.example.soccer_club_backend.dtos.tournamentteam;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DeleteTournamentTeamDTO {
    private int tournamentId;
    private int teamId;
}
