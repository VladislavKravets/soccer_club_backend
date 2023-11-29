package com.example.soccer_club_backend.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "photos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photoid")
    private Integer photoId;

//    @Column(name = "description")
//    private String description;

    @Column(name = "path")
    private String path;

    @Column(name = "tagid")
    private Integer tagId;

//    @ManyToOne
//    @JoinColumn(name = "news_id")
//    private News news;
}

