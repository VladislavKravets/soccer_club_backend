package com.example.soccer_club_backend.dtos.news;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewsDTO {

    @NotNull(message = "Not null title")
    private String title;

    @NotNull(message = "Not null content")
    private String content;

    @NotNull(message = "Not null publication Date")
    private LocalDate publicationDate;

//    @NotNull(message = "Not null author")
//    private String author;

    @NotNull(message = "Not null tag name")
    private String tagName;
}
