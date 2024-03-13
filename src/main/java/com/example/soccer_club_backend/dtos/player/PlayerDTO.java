package com.example.soccer_club_backend.dtos.player;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PlayerDTO {
    @NotNull(message = "Not null firstname")
    private String firstName;
    @NotNull(message = "Not null last name")
    private String lastName;
    @NotNull(message = "Not null birth date")
    private LocalDate birthDate;
    @NotNull(message = "Not null position")
    private String position;
    @NotNull(message = "Not null shirt number")
    private Integer shirtNumber;
    @NotNull(message = "Not null team id")
    private String teamName;
    @NotNull(message = "Not null active")
    private Boolean active;
}
