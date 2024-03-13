package com.example.soccer_club_backend.dtos.tournamentteam;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class TournamentTeamByTournamentId extends TournamentTeamDTO {
    private String photoUrl;

    public TournamentTeamByTournamentId(Integer teamid, String teamname) {
        super(teamid, teamname);
    }

    public TournamentTeamByTournamentId(Integer teamid, String teamname, String photoUrl) {
        super(teamid, teamname);
        this.photoUrl = photoUrl;
    }
}
