package com.example.soccer_club_backend.controllers;

import com.example.soccer_club_backend.dtos.tournament.TournamentDTO;
import com.example.soccer_club_backend.dtos.tournamentteam.DeleteTournamentTeamDTO;
import com.example.soccer_club_backend.dtos.tournamentteam.TournamentTeamDTO;
import com.example.soccer_club_backend.models.Tournament;
import com.example.soccer_club_backend.models.TournamentTeam;
import com.example.soccer_club_backend.service.TournamentTeamService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/tournament-team")
@AllArgsConstructor
public class TournamentTeamController {

    private final TournamentTeamService tournamentTeamService;

    @GetMapping("/{tournamentId}")
    public ResponseEntity<List<TournamentTeamDTO>> getAllTournamentTeam(@PathVariable int tournamentId) {
        List<TournamentTeamDTO> tournaments = tournamentTeamService.getAllTournamentTeams(tournamentId);
        return ResponseEntity.ok(tournaments);
    }

    @GetMapping("/getAllTeamByNotTournamentTeam/{tournamentId}")
    public ResponseEntity<List<TournamentTeamDTO>> getAllTournamentTeamOrNotFootballTeams(@PathVariable int tournamentId) {
        List<TournamentTeamDTO> tournaments = tournamentTeamService.getTournamentTeamsByNotTeams(tournamentId);
        return ResponseEntity.ok(tournaments);
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> createTournamentTeam(@RequestBody TournamentTeam tournament) {
        TournamentTeam createdTournament = tournamentTeamService.createTournamentTeam(tournament);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTournament);
    }

    @DeleteMapping
    @Secured("ROLE_ADMIN")
    public void deleteTournamentTeam(@RequestBody DeleteTournamentTeamDTO tournamentTeam) {
        tournamentTeamService.deleteTournamentTeam(tournamentTeam.getTournamentId(), tournamentTeam.getTeamId());
    }
}
