package com.example.soccer_club_backend.service;
import com.example.soccer_club_backend.dtos.player.PlayerStatsDTO;
import com.example.soccer_club_backend.dtos.player.PlayerDTO;
import com.example.soccer_club_backend.exceptions.ResourceNotFoundException;
import com.example.soccer_club_backend.models.FootballTeam;
import com.example.soccer_club_backend.models.Player;
import com.example.soccer_club_backend.models.Tag;
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
    public List<Player> getPlayerByTeamId(int teamId) {
        return playerRepository.findActivePlayersByTeamId(teamId);
    }

    public List<PlayerStatsDTO> getPlayerStats() {
        return playerRepository.getPlayerStatsDTO();
    }

    public Player createPlayer(PlayerDTO playerDTO) {
        FootballTeam footballTeam = footballTeamRepository.findByTeamName(playerDTO.getTeamName());

        if(footballTeam == null) throw new IllegalArgumentException("Не знайдено такої команди");
        Player newPlayer = new Player();
        newPlayer.setTeamId(footballTeam.getTeamId());
        modelMapper.map(playerDTO,newPlayer);
        return playerRepository.save(newPlayer);
    }

    public Player updatePlayer(int playerId, PlayerDTO updatedPlayer) {
        FootballTeam footballTeam = footballTeamRepository.findByTeamName(updatedPlayer.getTeamName());

        if(footballTeam == null) throw new IllegalArgumentException("Не знайдено такої команди");

        Player newPlayer = playerRepository.findById(playerId).orElseThrow(
                () -> new ResourceNotFoundException("Player not found id : " + playerId)
        );
        newPlayer.setTeamId(footballTeam.getTeamId());
        modelMapper.map(updatedPlayer, newPlayer); // copy field
        return playerRepository.save(newPlayer);
    }

    public boolean deletePlayer(int playerId) {
        if (playerRepository.existsById(playerId)) {
            playerRepository.deleteById(playerId);
            return true;
        }
        return false;
    }
}
