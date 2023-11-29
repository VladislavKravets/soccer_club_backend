package com.example.soccer_club_backend.repository;

import com.example.soccer_club_backend.models.Refer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefereeRepository extends JpaRepository<Refer, Integer> {
}
