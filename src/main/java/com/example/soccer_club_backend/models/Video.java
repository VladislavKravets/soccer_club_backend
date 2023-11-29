package com.example.soccer_club_backend.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "videos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "videoid")
    private Integer videoId;

    @Column(name = "description")
    private String description;

    @Column(name = "path")
    private String path;

    @Column(name = "tagid")
    private Integer tagId;
}

