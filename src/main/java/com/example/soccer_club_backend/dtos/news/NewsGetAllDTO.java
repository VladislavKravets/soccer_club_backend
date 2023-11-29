package com.example.soccer_club_backend.dtos.news;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class NewsGetAllDTO {
    private Integer newsId;
    @NotNull(message = "Not null title")
    private String title;
    @NotNull(message = "Not null content")
    private String content;
    @NotNull(message = "Not null publicationDate")
    private LocalDate publicationDate;
    //    @NotNull(message = "Not null author")
//    private String author;
    @NotNull(message = "Not null tagId")
    private String tagName;
    private String urlPhoto;
}
