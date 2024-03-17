package com.example.soccer_club_backend.controllers;

import com.example.soccer_club_backend.dtos.tournament.*;
import com.example.soccer_club_backend.models.Tournament;
import com.example.soccer_club_backend.service.FileStorageService;
import com.example.soccer_club_backend.service.TournamentService;
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
@RequestMapping("/api/tournament")
@AllArgsConstructor
public class TournamentController {

    private final TournamentService tournamentService;

    @GetMapping
    public ResponseEntity<List<TournamentGetInfo>> getAllTournament() {
        List<TournamentGetInfo> tournaments = tournamentService.getAllTournament();
        return ResponseEntity.ok(tournaments);
    }

    @GetMapping("/getTournamentInfoById/{tournamentId}")
    public ResponseEntity<TournamentGetInfo> getTournamentInfoById(@PathVariable int tournamentId) {
        TournamentGetInfo tournaments = tournamentService.getTournamentInfoById(tournamentId);
        return ResponseEntity.ok(tournaments);
    }

    @GetMapping("/getAllNewsByTourId/{tournamentId}")
    public ResponseEntity<List<TournamentNews>> getAllNewsTournament(@PathVariable int tournamentId) {
        List<TournamentNews> newsList = tournamentService.getAllTournamentNewsByIdTour(tournamentId);
        return ResponseEntity.ok(newsList);
    }

    @GetMapping("/getAllPhotoByTourId/{tournamentId}")
    public ResponseEntity<List<TournamentPhoto>> getAllPhotoTournament(@PathVariable int tournamentId) {
        List<TournamentPhoto> photoList = tournamentService.getAllTournamentPhotoByIdTour(tournamentId);
        return ResponseEntity.ok(photoList);
    }

    @GetMapping("/getTournamentTable/{tournamentId}")
    public ResponseEntity<List<TournamentTeamStatsDTO>> getTournamentTableByIdTour(@PathVariable int tournamentId) {
        List<TournamentTeamStatsDTO> table = tournamentService.getTournamentTableByIdTour(tournamentId);
        return ResponseEntity.ok(table);
    }

    @GetMapping("/{tournamentId}")
    public ResponseEntity<Tournament> getTournamentId(@PathVariable int tournamentId) {
        Tournament tournament = tournamentService.getTournamentGetId(tournamentId);
        return ResponseEntity.ok(tournament);
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> createTournament(@RequestPart("file") MultipartFile file, @RequestPart("tournament") TournamentDTO tournamentDTO) {
        Tournament createdTournament = tournamentService.createTournament(tournamentDTO, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTournament);
    }

    @PutMapping(value = "/{tournamentId}",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Tournament> updateTournament(@PathVariable int tournamentId,
                                                       @RequestPart("tournament") @Valid TournamentDTO tournamentDTO,
                                                       @RequestPart("file") @RequestParam(required = false) MultipartFile file) {
        Tournament updatedTournament = tournamentService.updateTournament(tournamentId, tournamentDTO, file);
        return ResponseEntity.ok(updatedTournament);
    }

    @DeleteMapping("/{tournamentId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> deleteNews(@PathVariable int tournamentId) {
        if (tournamentService.deleteTournament(tournamentId)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
