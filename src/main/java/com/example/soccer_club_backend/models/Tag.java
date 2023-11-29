package com.example.soccer_club_backend.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "tags")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tagid")
    private Integer tagId;

    @Column(name = "tagname")
    private String tagName;

    public Tag setTagName(String tagName) {
        this.tagName = tagName;
        return this;
    }
}
