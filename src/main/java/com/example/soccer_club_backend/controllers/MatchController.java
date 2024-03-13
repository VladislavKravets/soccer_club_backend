package com.example.soccer_club_backend.controllers;

import com.example.soccer_club_backend.dtos.match.GetIDMatchDTO;
import com.example.soccer_club_backend.dtos.match.MatchAllByTourId;
import com.example.soccer_club_backend.dtos.match.MatchDTO;
import com.example.soccer_club_backend.dtos.news.NewsDTO;
import com.example.soccer_club_backend.dtos.news.NewsGetAllDTO;
import com.example.soccer_club_backend.mapper.NewsMapper;
import com.example.soccer_club_backend.models.Match;
import com.example.soccer_club_backend.models.News;
import com.example.soccer_club_backend.service.FileStorageService;
import com.example.soccer_club_backend.service.MatchService;
import com.example.soccer_club_backend.service.NewsService;
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
@RequestMapping("/api/match")
@CrossOrigin(origins = "http://localhost:3000") // Разрешить запросы с указанного источника
@AllArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @GetMapping
    public List<Match> getAllMatches() {
        return matchService.getAllMatch();
    }

    @GetMapping("/{matchId}")
    public GetIDMatchDTO getMatchById(@PathVariable int matchId) {
        return matchService.getMatchById(matchId);
    }

    @GetMapping("/getAllMathByTourId/{tourId}")
    public List<MatchAllByTourId> getAllMatchByTourId(@PathVariable int tourId) {
        return matchService.getAllMatchByTournamentId(tourId);
    }

    @PostMapping()
    @Secured("ROLE_ADMIN")
    public Match createMatch(@RequestBody MatchDTO matchDTO) {
        return matchService.createMatch(matchDTO);
    }

    @PutMapping(value = "/{matchId}")
    @Secured("ROLE_ADMIN")
    public Match updateMatch(@PathVariable int matchId, @RequestBody @Valid MatchDTO updateMatchDto) {
        return matchService.updateMatch(matchId, updateMatchDto);
    }

    @DeleteMapping("/{matchId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> deleteMatch(@PathVariable int matchId) {
        if (matchService.deleteMatch(matchId)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
