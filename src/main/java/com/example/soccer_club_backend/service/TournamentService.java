package com.example.soccer_club_backend.service;

import com.example.soccer_club_backend.dtos.tournament.*;
import com.example.soccer_club_backend.exceptions.ResourceNotFoundException;
import com.example.soccer_club_backend.models.Photo;
import com.example.soccer_club_backend.models.Tag;
import com.example.soccer_club_backend.models.Tournament;
import com.example.soccer_club_backend.repository.PhotoRepository;
import com.example.soccer_club_backend.repository.TagRepository;
import com.example.soccer_club_backend.repository.TournamentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TournamentService {
    private final TournamentRepository tournamentRepository;
    private final TagRepository tagRepository;
    private final PhotoRepository photoRepository;
//    private final FileStorageService fileStorageService;
    private final CloudinaryService cloudinaryService;


    private final ModelMapper modelMapper = new ModelMapper();

    public List<TournamentGetInfo> getAllTournament() {
        return tournamentRepository.getAllTournament();
    }
    public TournamentGetInfo getTournamentInfoById(int tournamentId) {
        tournamentRepository.findById(tournamentId).orElseThrow(
                () -> new ResourceNotFoundException("Tournament not found id : " + tournamentId)
        );
        return tournamentRepository.getTournamentInfoById(tournamentId);
    }

    public List<TournamentNews> getAllTournamentNewsByIdTour(int idTour) {
        return tournamentRepository.getTournamentAllNews(idTour);
    }
    @SneakyThrows
    public List<TournamentTeamStatsDTO> getTournamentTableByIdTour(int idTour) {
        return tournamentRepository.getTableTournamentByTournamentId(idTour);
    }

    public List<TournamentPhoto> getAllTournamentPhotoByIdTour(int idTour) {
        return tournamentRepository.getTournamentAllPhoto(idTour);
    }

    public Tournament getTournamentGetId(int tournamentId) {
        return tournamentRepository.findById(tournamentId).orElseThrow(
                () -> new ResourceNotFoundException("Tournament not found id : " + tournamentId)
        );
    }

    public Tournament createTournament(TournamentDTO tournamentDTO, MultipartFile file) {
        if(tagRepository.findByTagName(tournamentDTO.getTagName()) != null) {
            throw new ResourceNotFoundException("Tурнір з таким тегом вже існує в базі даних");
        }

        if (file == null) {
            throw  new ResourceNotFoundException("File is required for creating tournament.");
        }

        Tag tag = tagRepository.save(new Tag().setTagName(tournamentDTO.getTagName().toLowerCase()));

        Tournament tournament = new Tournament();
        modelMapper.map(tournamentDTO, tournament);

        tournament.setTag(tag);
        tournamentRepository.save(tournament);

        Long tournamentId = Long.valueOf(tournament.getTournamentId());
        Photo photo = new Photo();
        photo.setPhotoId(Math.toIntExact(tournamentId));

//        String fileName = fileStorageService.storeFile(file);
        photo.setPath(cloudinaryService.uploadFile(file, "folder_1"));
        photo.setTagId(tag.getTagId());

        photoRepository.save(photo);
        tournament.setPhoto(photo);
        return tournamentRepository.save(tournament);
    }

    public Tournament updateTournament(int id, TournamentDTO updatedTournament, MultipartFile file) {
        Tournament tournament = tournamentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Tournament not found id : " + id)
        );

        Tag tag = tagRepository.findByTagName(updatedTournament.getTagName());

        if (tag == null) {
            tag = tournament.getTag();
        }

        // Оновлюємо назву тега
        tag.setTagName(updatedTournament.getTagName());
        tagRepository.save(tag);

        if (file != null) {
            photoRepository.delete(tournament.getPhoto());
            Photo photo = new Photo();
            photo.setPhotoId(Math.toIntExact(tournament.getTournamentId()));
//            String filePath = fileStorageService.storeFile(file);
            photo.setPath(cloudinaryService.uploadFile(file, "folder_1"));
            photo.setTagId(tag.getTagId());
            photoRepository.save(photo);
            tournament.setPhoto(photo);
        }

        modelMapper.map(updatedTournament, tournament); // копіює поля
        tournament.setTag(tag);

        return tournamentRepository.save(tournament);
    }

    @Transactional
    public boolean deleteTournament(int tournamentId) {
        if (tournamentRepository.existsById(tournamentId)) {
//            Tournament tournament = tournamentRepository.findById(tournamentId).orElseThrow(
//                    () -> new ResourceNotFoundException("Tournament not found id : " + tournamentId)
//            );
            tournamentRepository.deleteById(tournamentId);
//            tagRepository.delete(tournament.getTag());
            return true;
        }
        return false;
    }
}
