package com.example.soccer_club_backend.service;

import com.example.soccer_club_backend.dtos.tournamentteam.TournamentTeamDTO;
import com.example.soccer_club_backend.exceptions.ResourceNotFoundException;
import com.example.soccer_club_backend.models.TournamentTeam;
import com.example.soccer_club_backend.repository.PhotoRepository;
import com.example.soccer_club_backend.repository.TagRepository;
import com.example.soccer_club_backend.repository.TournamentRepository;
import com.example.soccer_club_backend.repository.TournamentTeamRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TournamentTeamService {
    private final TournamentTeamRepository tournamentTeamRepository;
    private final TournamentRepository tournamentRepository;
    private final TagRepository tagRepository;
    private final PhotoRepository photoRepository;
    private final FileStorageService fileStorageService;


    private final ModelMapper modelMapper = new ModelMapper();

    public List<TournamentTeamDTO> getAllTournamentTeams(int tournamentId) {
        tournamentRepository.findById(tournamentId).orElseThrow(
                () -> new ResourceNotFoundException("Tournament not found id : " + tournamentId)
        );
        return tournamentTeamRepository.getTournamentTeamsByTeams(tournamentId);
    }

    public List<TournamentTeamDTO> getTournamentTeamsByNotTeams(int tournamentId) {
        tournamentRepository.findById(tournamentId).orElseThrow(
                () -> new ResourceNotFoundException("Tournament not found id : " + tournamentId)
        );
        return tournamentTeamRepository.getTournamentTeamsByNotTeams(tournamentId);
    }
//    public TournamentGetInfo getTournamentInfoById(int tournamentId) {
//        tournamentRepository.findById(tournamentId).orElseThrow(
//                () -> new ResourceNotFoundException("Tournament not found id : " + tournamentId)
//        );
//        return tournamentRepository.getTournamentInfoById(tournamentId);
//    }

    public TournamentTeam createTournamentTeam(TournamentTeam tournament) {
        return tournamentTeamRepository.save(tournament);
    }

//    public Tournament updateTournament(int id, TournamentDTO updatedTournament, MultipartFile file) {
//        Tournament tournament = tournamentRepository.findById(id).orElseThrow(
//                () -> new ResourceNotFoundException("Tournament not found id : " + id)
//        );
//
//        Tag tag = tagRepository.findByTagName(updatedTournament.getTagName());
//
//        if (tag == null) {
//            tag = tournament.getTag();
//        }
//
//        // Оновлюємо назву тега
//        tag.setTagName(updatedTournament.getTagName());
//        tagRepository.save(tag);
//
//        if (file != null) {
//            photoRepository.delete(tournament.getPhoto());
//            Photo photo = new Photo();
//            photo.setPhotoId(Math.toIntExact(tournament.getTournamentId()));
//            String filePath = fileStorageService.storeFile(file);
//            photo.setPath(filePath);
//            photo.setTagId(tag.getTagId());
//            photoRepository.save(photo);
//            tournament.setPhoto(photo);
//        }
//
//        modelMapper.map(updatedTournament, tournament); // копіює поля
//        tournament.setTag(tag);
//
//        return tournamentRepository.save(tournament);
//    }


    public void deleteTournamentTeam(int tournamentId, int teamId) {
        tournamentRepository.findById(tournamentId).orElseThrow(
                () -> new ResourceNotFoundException("Tournament not found id : " + tournamentId)
        );
        tournamentTeamRepository.deleteByTournamentIdAndTeamId(tournamentId, teamId);
    }
}
