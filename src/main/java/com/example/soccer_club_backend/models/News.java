package com.example.soccer_club_backend.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "news")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "newsid")
    private Integer newsId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "publicationdate")
    private LocalDate publicationDate;

    @Column(name = "author")
    private String author;

//    @Column(name = "tagid")
    @ManyToOne
    private Tag tag;

//    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Photo> photos;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "photo_id")
    private Photo photo;
}
