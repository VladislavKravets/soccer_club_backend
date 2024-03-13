package com.example.soccer_club_backend.repository;

import com.example.soccer_club_backend.dtos.footballTeam.FootballTeamInfo;
import com.example.soccer_club_backend.dtos.match.GetIDMatchDTO;
import com.example.soccer_club_backend.dtos.match.MatchAllByTourId;
import com.example.soccer_club_backend.dtos.tournament.TournamentPhoto;
import com.example.soccer_club_backend.models.Card;
import com.example.soccer_club_backend.models.Match;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
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

    @Query(nativeQuery = true, value = "SELECT\n" +
            "    t.tournamentname AS tournament_name,\n" +
            "    m.tournamentid AS tournament_id,\n" +
            "    to_char(m.matchdate, 'Day') AS match_day,\n" + // Форматирование дня недели
            "    m.matchdate AS match_date,\n" + // Дата матча
            "    m.matchtime AS match_time,\n" + // Время матча
            "    team1.teamname AS team1_name,\n" +
            "    team1.teamid AS team1_id,\n" +
            "    team2.teamname AS team2_name,\n" +
            "    team2.teamid AS team2_id,\n" +
            "    m.team1totalgoals || ' - ' || m.team2totalgoals AS score,\n" +
            "    p1.path AS team1_patchUrl,\n" + // Путь к изображению для первой команды
            "    p2.path AS team2_patchUrl\n" + // Путь к изображению для второй команды
            "FROM\n" +
            "    public.matches m\n" +
            "INNER JOIN\n" +
            "    public.tournaments t ON m.tournamentid = t.tournamentid\n" +
            "INNER JOIN\n" +
            "    public.footballteams team1 ON m.team1id = team1.teamid\n" +
            "INNER JOIN\n" +
            "    public.footballteams team2 ON m.team2id = team2.teamid\n" +
            "LEFT JOIN\n" +
            "    public.photos p1 ON team1.photo_id = p1.photoid\n" + // JOIN с таблицей photos для первой команды
            "LEFT JOIN\n" +
            "    public.photos p2 ON team2.photo_id = p2.photoid\n" + // JOIN с таблицей photos для второй команды
            "WHERE\n" +
            "    m.matchid = :matchId")
    Tuple gMatchById(@Param("matchId") Integer matchId);

    default GetIDMatchDTO getMatchById(int matchId) {
        Tuple tuple = gMatchById(matchId);

        String tournamentName = tuple.get("tournament_name", String.class);
        int tournamentId = tuple.get("tournament_id", Integer.class);
        Date matchDate = tuple.get("match_date", Date.class);
        Time matchTime = tuple.get("match_time", Time.class);
        String matchDay = tuple.get("match_day", String.class);
        String team1Name = tuple.get("team1_name", String.class);
        String team2Name = tuple.get("team2_name", String.class);
        Integer team1Id = tuple.get("team1_id", Integer.class);
        Integer team2Id = tuple.get("team2_id", Integer.class);
        String teamPhoto1 = tuple.get("team1_patchUrl", String.class);
        String teamPhoto2 = tuple.get("team2_patchUrl", String.class);
        String score = tuple.get("score", String.class);

        return new GetIDMatchDTO(tournamentName, tournamentId, matchDate, matchTime, matchDay, team1Name, team2Name, team1Id, team2Id, teamPhoto1, teamPhoto2, score);
    }
}
