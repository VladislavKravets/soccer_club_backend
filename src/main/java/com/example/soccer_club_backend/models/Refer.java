package com.example.soccer_club_backend.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "referees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Refer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refereeid")
    private Integer refereeId;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "qualificationlevel")
    private String qualificationLevel;

    @Column(name = "association")
    private String association;

    @Column(name = "tagid")
    private Integer tagId;
}
