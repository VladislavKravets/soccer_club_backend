package com.example.soccer_club_backend.repository;

import com.example.soccer_club_backend.models.Card;
import com.example.soccer_club_backend.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    Tag findByTagName(String tagName);
}
