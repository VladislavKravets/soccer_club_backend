package com.example.soccer_club_backend.repository;

import com.example.soccer_club_backend.models.Card;
import com.example.soccer_club_backend.models.TournamentSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentSettingsRepository extends JpaRepository<TournamentSettings, Integer> {
}
