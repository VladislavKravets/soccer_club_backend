package com.example.soccer_club_backend.service;

import com.example.soccer_club_backend.dtos.FootballTeamDTO;
import com.example.soccer_club_backend.dtos.footballTeam.FootballTeamInfo;
import com.example.soccer_club_backend.dtos.footballTeam.FootballTeams;
import com.example.soccer_club_backend.dtos.footballTeam.MatchInfo;
import com.example.soccer_club_backend.exceptions.ResourceNotFoundException;
import com.example.soccer_club_backend.models.FootballTeam;
import com.example.soccer_club_backend.models.Photo;
import com.example.soccer_club_backend.models.Tag;
import com.example.soccer_club_backend.repository.FootballTeamRepository;
import com.example.soccer_club_backend.repository.PhotoRepository;
import com.example.soccer_club_backend.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FootballTeamService {
    private final FootballTeamRepository footballTeamRepository;
    private final TagRepository tagRepository;
    private final FileStorageService fileStorageService;
    private final ModelMapper modelMapper = new ModelMapper();

    public List<FootballTeams> getAllFootballTeam() {
        return footballTeamRepository.getAllFootballTeams();
    }

    public FootballTeam getFootballTeam(int footballTeamId) {
        return footballTeamRepository.findById(footballTeamId).orElseThrow(
                () -> new ResourceNotFoundException("Football team not found : " + footballTeamId)
        );
    }


    @SneakyThrows
    public FootballTeamInfo getInfoByFootballTeamId(int footballTeamId) {
        return footballTeamRepository.getInfoByTeamId(footballTeamId);
    }


    public List<MatchInfo> getMatchesByFootballTeamId(int footballTeamId) {
        return footballTeamRepository.getMatchesByTeamId(footballTeamId);
    }

    public FootballTeam createFootballTeam(FootballTeamDTO footballTeamDTO, MultipartFile file) {
        Tag tag = tagRepository.findByTagName(footballTeamDTO.getTagName());

        if (tag == null) {
            tag = tagRepository.save(new Tag().setTagName(footballTeamDTO.getTagName()));
        }else {
            throw new IllegalArgumentException("Тег '" + footballTeamDTO.getTagName() + "' вже існує");
        }

        FootballTeam footballTeam = new FootballTeam();
        modelMapper.map(footballTeamDTO,footballTeam);

        Photo photo = new Photo();

        String fileName = fileStorageService.storeFile(file);
        photo.setPath(fileName);

        photo.setTagId(tag.getTagId());

        footballTeam.setTag(tag);
        FootballTeam footballTeam1 = footballTeamRepository.save(footballTeam);
        footballTeam1.setPhoto(photo);

        return footballTeamRepository.save(footballTeam);
    }

    public FootballTeam updateFootballTeam(int teamId, FootballTeamDTO footballTeamDTO, String urlPatch) {
        FootballTeam footballTeam = footballTeamRepository.findById(teamId).orElseThrow(
                () -> new ResourceNotFoundException("football team not found id : " + teamId)
        );
        Tag tag = tagRepository.findByTagName(footballTeamDTO.getTagName());
        if (tag == null) {
            tag = tagRepository.save(new Tag().setTagName(footballTeamDTO.getTagName()));
        }
        modelMapper.map(footballTeamDTO,footballTeam);

        footballTeam.setTag(tag);

        if(urlPatch != null) {
            Photo photo = new Photo();
            photo.setPath(urlPatch);
            photo.setTagId(tag.getTagId());
            footballTeam.setPhoto(photo);
        }

        return footballTeamRepository.save(footballTeam);
    }

    public boolean deleteFootballTeam(int playerId) {
        if (footballTeamRepository.existsById(playerId)) {
            footballTeamRepository.deleteById(playerId);
            return true;
        }
        return false;
    }
}
