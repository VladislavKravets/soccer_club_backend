package com.example.soccer_club_backend.service;

import com.example.soccer_club_backend.dtos.ReferDTO;
import com.example.soccer_club_backend.exceptions.ResourceNotFoundException;
import com.example.soccer_club_backend.models.Refer;
import com.example.soccer_club_backend.repository.RefereeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReferService {
    private final RefereeRepository refereeRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public List<Refer> getAllRefers() {
        return refereeRepository.findAll();
    }

    public Refer getReferId(int referId) {
        return refereeRepository.findById(referId).orElseThrow(
                () -> new ResourceNotFoundException("Refer not found id : " + referId)
        );
    }

    public Refer createRefer(ReferDTO referDTO) {
        Refer referee = new Refer();
        modelMapper.map(referDTO,referee);
        return refereeRepository.save(referee);
    }

//    public Player updatePlayer(int playerId, PlayerDTO updatedPlayer) {
//        footballTeamRepository.findById(updatedPlayer.getTeamId()).orElseThrow(
//                () -> new ResourceNotFoundException("football team not found id : " + updatedPlayer.getTeamId())
//        );
//
//        Player player = playerRepository.findById(playerId).orElseThrow(
//                () -> new ResourceNotFoundException("Player not found id : " + playerId)
//        );
//        modelMapper.map(updatedPlayer, player); // copy field
//        return playerRepository.save(player);
//    }
//
//    public boolean deletePlayer(int playerId) {
//        if (playerRepository.existsById(playerId)) {
//            playerRepository.deleteById(playerId);
//            return true;
//        }
//        return false;
//    }
}
