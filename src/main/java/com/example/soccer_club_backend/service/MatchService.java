package com.example.soccer_club_backend.service;

import com.example.soccer_club_backend.dtos.match.MatchAllByTourId;
import com.example.soccer_club_backend.dtos.match.MatchDTO;
import com.example.soccer_club_backend.exceptions.ResourceNotFoundException;
import com.example.soccer_club_backend.models.Match;
import com.example.soccer_club_backend.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchRepository matchRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    public List<Match> getAllMatch() {
        return matchRepository.findAll();
    }

    public List<MatchAllByTourId> getAllMatchByTournamentId(int tournamentId) {
        return matchRepository.getAllMatchByTourId(tournamentId);
    }

    public Match getMatchById(int matchId) {
        return matchRepository.findById(matchId).orElseThrow(
                () -> new ResourceNotFoundException("Match not found id : " + matchId)
        );
    }

    public Match createMatch(MatchDTO matchDTO) {
        // Використання конфігурації ModelMapper для ігнорування поля matchId
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        Match match = new Match();
        // Мапінг інших полів (matchId буде проігноровано)
        modelMapper.map(matchDTO, match);
        return matchRepository.save(match);
    }


    public Match updateMatch(int id, MatchDTO matchDTO) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        Match match = matchRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Match not found id : " + id)
        );
        modelMapper.map(matchDTO, match);
        return matchRepository.save(match);
    }

    public boolean deleteMatch(int id) {
        if (matchRepository.existsById(id)) {
            matchRepository.deleteById(id);
            return true;
        }
        return false; // Handle not found case as needed
    }
}
