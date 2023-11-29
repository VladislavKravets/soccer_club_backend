package com.example.soccer_club_backend.mapper;

import com.example.soccer_club_backend.dtos.FootballTeamDTO;
import com.example.soccer_club_backend.models.FootballTeam;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FootballTeamMapper {
    @Mapping(source = "tag.tagName", target = "tagName")
//    @Mapping(source = "photo.path", target = "pathUrl")
    FootballTeamDTO toFootballTeam(FootballTeam footballTeam);

    @IterableMapping(elementTargetType = FootballTeamDTO.class)
    List<FootballTeamDTO> toFootballTeamDTO(List<FootballTeam> footballTeams);
}
