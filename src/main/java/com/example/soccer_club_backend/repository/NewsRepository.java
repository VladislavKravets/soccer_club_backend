package com.example.soccer_club_backend.repository;

import com.example.soccer_club_backend.models.Card;
import com.example.soccer_club_backend.models.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
}
