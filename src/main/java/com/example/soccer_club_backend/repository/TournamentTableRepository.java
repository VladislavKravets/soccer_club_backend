package com.example.soccer_club_backend.repository;

import com.example.soccer_club_backend.models.Card;
import com.example.soccer_club_backend.models.TournamentTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentTableRepository extends JpaRepository<TournamentTable, Integer> {
}
