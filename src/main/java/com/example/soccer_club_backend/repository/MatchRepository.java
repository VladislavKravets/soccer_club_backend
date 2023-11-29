package com.example.soccer_club_backend.repository;

import com.example.soccer_club_backend.dtos.match.MatchAllByTourId;
import com.example.soccer_club_backend.dtos.tournament.TournamentPhoto;
import com.example.soccer_club_backend.models.Card;
import com.example.soccer_club_backend.models.Match;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer> {
    @Query(nativeQuery = true, value = "SELECT m.matchId,\n" +
            "\t   m.matchdate, \n" +
            "       TO_CHAR(m.matchtime, 'HH24:MI') as matchtime,\n" +
            "       to_char(m.matchdate, 'FMDay') AS dayOfWeek,\n" +
            "       m.team1totalgoals AS team1TotalGoals, \n" +
            "       m.team2totalgoals AS team2TotalGoals, \n" +
            "       team1.teamname AS teamName1, \n" +
            "       team1.teamid AS teamId1, \n" +
            "       team2.teamname AS teamName2,\n" +
            "       team2.teamid AS teamId2,\n" +
            "       m.group_stage\n" +
            "FROM public.matches m\n" +
            "INNER JOIN public.footballteams team1 ON m.team1id = team1.teamid\n" +
            "INNER JOIN public.footballteams team2 ON m.team2id = team2.teamid\n" +
            "WHERE m.tournamentid = :tournamentId\n" +
            "ORDER BY m.matchdate, m.matchid;")
    List<Tuple> getAllMatchesByTournamentId(@Param("tournamentId") Integer tournamentId);

    default List<MatchAllByTourId> getAllMatchByTourId(int tournamentId) {
        List<Tuple> tuples = getAllMatchesByTournamentId(tournamentId);
        return tuples.stream()
                .map(tuple -> new MatchAllByTourId(
                        tuple.get("matchId", Integer.class),
                        tuple.get("matchDate", Date.class),
                        tuple.get("matchTime", String.class),
                        tuple.get("dayOfWeek", String.class),
                        tuple.get("team1TotalGoals", Integer.class),
                        tuple.get("team2TotalGoals", Integer.class),
                        tuple.get("teamName1", String.class),
                        tuple.get("teamId1", Integer.class),
                        tuple.get("teamName2", String.class),
                        tuple.get("teamId2", Integer.class),
                        tuple.get("group_stage", Integer.class)))
                .collect(Collectors.toList());
    }
}
