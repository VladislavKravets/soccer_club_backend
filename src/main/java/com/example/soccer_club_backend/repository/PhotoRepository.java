package com.example.soccer_club_backend.repository;

import com.example.soccer_club_backend.models.Card;
import com.example.soccer_club_backend.models.Photo;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

    @Repository
    public interface PhotoRepository extends JpaRepository<Photo, Integer> {
        @Lock(LockModeType.NONE)
        @Query("DELETE FROM Photo p WHERE p.id = :photoId")
        void deleteById(@Param("photoId") Long photoId);
    }
