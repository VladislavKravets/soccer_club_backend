package com.example.soccer_club_backend.service;
import com.example.soccer_club_backend.dtos.player.PlayerStatsDTO;
import com.example.soccer_club_backend.dtos.player.PlayerDTO;
import com.example.soccer_club_backend.exceptions.ResourceNotFoundException;
import com.example.soccer_club_backend.models.Player;
import com.example.soccer_club_backend.repository.FootballTeamRepository;
import com.example.soccer_club_backend.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final FootballTeamRepository footballTeamRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Optional<Player> getPlayerId(int playerId) {
        return playerRepository.findById(playerId);
    }

    public List<PlayerStatsDTO> getPlayerStats() {
        return playerRepository.getPlayerStatsDTO();
    }

    public Player createPlayer(PlayerDTO playerDTO) {
        footballTeamRepository.findById(playerDTO.getTeamId()).orElseThrow(
                () -> new ResourceNotFoundException("football team not found id : " + playerDTO.getTeamId())
        );
        Player player = new Player();
        modelMapper.map(playerDTO,player);
        return playerRepository.save(player);
    }

    public Player updatePlayer(int playerId, PlayerDTO updatedPlayer) {
        footballTeamRepository.findById(updatedPlayer.getTeamId()).orElseThrow(
                () -> new ResourceNotFoundException("football team not found id : " + updatedPlayer.getTeamId())
        );

        Player player = playerRepository.findById(playerId).orElseThrow(
                () -> new ResourceNotFoundException("Player not found id : " + playerId)
        );
        modelMapper.map(updatedPlayer, player); // copy field
        return playerRepository.save(player);
    }

    public boolean deletePlayer(int playerId) {
        if (playerRepository.existsById(playerId)) {
            playerRepository.deleteById(playerId);
            return true;
        }
        return false;
    }
}
