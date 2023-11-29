package com.example.soccer_club_backend.controllers;

import com.example.soccer_club_backend.dtos.player.PlayerDTO;
import com.example.soccer_club_backend.dtos.player.PlayerStatsDTO;
import com.example.soccer_club_backend.models.Player;
import com.example.soccer_club_backend.service.PlayerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
@CrossOrigin(origins = "http://localhost:3000") // Разрешить запросы с указанного источника
@AllArgsConstructor
public class PlayerController {
    private final PlayerService playerService;

    @GetMapping
    public List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @GetMapping("/getPlayerStats")
    public List<PlayerStatsDTO> getPlayerStats() {
        return playerService.getPlayerStats();
    }

    @GetMapping("/{playerId}")
    public ResponseEntity<Player> getPlayerById(@PathVariable int playerId) {
        return playerService.getPlayerId(playerId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Player> createPlayer(@RequestBody @Valid PlayerDTO player) {
        Player createPlayer = playerService.createPlayer(player);
        return ResponseEntity.status(HttpStatus.CREATED).body(createPlayer);
    }

    @PutMapping("/{playerId}")
    @Secured("ROLE_ADMIN")
    public Player updatePlayer(@PathVariable int playerId, @RequestBody @Valid PlayerDTO updatePlayer) {
        return playerService.updatePlayer(playerId, updatePlayer);
    }

    @DeleteMapping("/{playerId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> deletePlayer(@PathVariable int playerId) {
        if (playerService.deletePlayer(playerId)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
