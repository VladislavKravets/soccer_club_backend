package com.example.soccer_club_backend.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FootballTeamDTO {
    @Null
    private Integer teamId;
    @NotNull(message = "Not null team name")
    private String teamName;
    @NotNull(message = "Not null coach")
    private String coach;
    @NotNull(message = "Not null district")
    private String district;
    @NotNull(message = "Not null tag name")
    private String tagName;
//    @NotNull(message = "Not null photo url")
//    private String pathUrl;
}
