package com.example.soccer_club_backend.controllers;

import com.example.soccer_club_backend.dtos.FootballTeamDTO;
import com.example.soccer_club_backend.dtos.footballTeam.FootballTeamInfo;
import com.example.soccer_club_backend.dtos.footballTeam.FootballTeams;
import com.example.soccer_club_backend.dtos.footballTeam.MatchInfo;
import com.example.soccer_club_backend.mapper.FootballTeamMapper;
import com.example.soccer_club_backend.models.FootballTeam;
import com.example.soccer_club_backend.service.FileStorageService;
import com.example.soccer_club_backend.service.FootballTeamService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/footballTeam")
@CrossOrigin(origins = "http://localhost:3000") // Разрешить запросы с указанного источника
@AllArgsConstructor
public class FootballTeamController {
    private FootballTeamService footballTeamService;
    private FileStorageService fileStorageService;
    private FootballTeamMapper footballTeamMapper;

    @GetMapping
    public List<FootballTeams> getAllFootballTeams() {
        return footballTeamService.getAllFootballTeam();
    }

    @GetMapping("/{footballTeamId}")
    public FootballTeamDTO getFootballTeamId(@PathVariable int footballTeamId) {
        return footballTeamMapper.toFootballTeam(footballTeamService.getFootballTeam(footballTeamId));
    }
    @GetMapping("/getInfo/{footballTeamId}")
    public FootballTeamInfo getInfoByFootballTeamId(@PathVariable int footballTeamId) {
        return footballTeamService.getInfoByFootballTeamId(footballTeamId);
    }

    @GetMapping("/getMatches/{footballTeamId}")
    public List<MatchInfo> getMatchesByFootballTeamId(@PathVariable int footballTeamId) {
        return footballTeamService.getMatchesByFootballTeamId(footballTeamId);
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public FootballTeam createFootballTeam(@RequestPart @Valid FootballTeamDTO footballTeam, @RequestPart("file") MultipartFile file) {
        return footballTeamService.createFootballTeam(footballTeam, file);
    }

    @PutMapping("/{footballTeamId}")
    @Secured("ROLE_ADMIN")
    public FootballTeam updateTeam(@PathVariable int footballTeamId, @RequestBody @Valid FootballTeamDTO footballTeamDTO,
                                   @RequestPart("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);
        return footballTeamService.updateFootballTeam(footballTeamId, footballTeamDTO,fileName);
    }

    @DeleteMapping("/{footballTeamId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> deleteFootballTeam(@PathVariable int footballTeamId) {
        if (footballTeamService.deleteFootballTeam(footballTeamId)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
