package com.example.soccer_club_backend.repository;

//import aj.org.objectweb.asm.TypeReference;

import com.example.soccer_club_backend.dtos.tournament.TournamentGetInfo;
import com.example.soccer_club_backend.dtos.tournament.TournamentNews;
import com.example.soccer_club_backend.dtos.tournament.TournamentPhoto;
import com.example.soccer_club_backend.dtos.tournament.TournamentTeamStatsDTO;
import com.example.soccer_club_backend.models.Tournament;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Tournament t WHERE t.tournamentId = :tournamentId")
    void deleteById(@Param("tournamentId") int tournamentId);

    @Query(nativeQuery = true, value = "SELECT\n" +
            "\tt.tournamentid as \"tournamentid\",\n" +
            "    t.tournamentname AS \"tournamentname\",\n" +
            "    p.path AS \"photoUrl\",\n" +
            "    t.startdate AS \"startDate\",\n" +
            "    t.enddate AS \"endDate\",\n" +
            "    COUNT(tt.teamid) AS \"countTeam\",\n" +
            "    CASE\n" +
            "        WHEN t.enddate < CURRENT_DATE THEN 'Турнір завершено'\n" +
            "        ELSE 'Турнір не завершено'\n" +
            "    END AS \"status\",\n" +
            "    tg.tagname AS \"tagName\"\n" +
            "FROM\n" +
            "    tournaments t\n" +
            "LEFT JOIN\n" +
            "    photos p ON t.photo_id = p.photoid\n" +
            "LEFT JOIN\n" +
            "    tournamentteams tt ON t.tournamentid = tt.tournamentid\n" +
            "LEFT JOIN\n" +
            "    tags tg ON t.tag_tagid = tg.tagid\n" +
            "GROUP BY\n" +
            "    t.tournamentid, p.path, tg.tagname\n" +
            "ORDER BY\n" +
            "    t.startdate;\n")
    List<Tuple> getTournamentAll();

    default List<TournamentGetInfo> getAllTournament() {
        List<Tuple> tuples = getTournamentAll();

        return tuples.stream()
                .map(tuple -> new TournamentGetInfo(
                        tuple.get("tournamentId", Integer.class),
                        tuple.get("tournamentName", String.class),
                        tuple.get("photoUrl", String.class),
                        tuple.get("startDate", Date.class),
                        tuple.get("endDate", Date.class),
                        tuple.get("countTeam", Long.class),
                        tuple.get("status", String.class),
                        tuple.get("tagname", String.class)))
                .collect(Collectors.toList());
    }

    @Query(nativeQuery = true, value = "SELECT\n" +
            "\tt.tournamentid as \"tournamentid\",\n" +
            "    t.tournamentname AS \"tournamentname\",\n" +
            "    p.path AS \"photoUrl\",\n" +
            "    t.startdate AS \"startDate\",\n" +
            "    t.enddate AS \"endDate\",\n" +
            "    COUNT(tt.teamid) AS \"countTeam\",\n" +
            "    CASE\n" +
            "        WHEN t.enddate < CURRENT_DATE THEN 'Турнір завершено'\n" +
            "        ELSE 'Турнір не завершено'\n" +
            "    END AS \"status\",\n" +
            "    tg.tagname AS \"tagName\"\n" +
            "FROM\n" +
            "    tournaments t\n" +
            "LEFT JOIN\n" +
            "    photos p ON t.photo_id = p.photoid\n" +
            "LEFT JOIN\n" +
            "    tournamentteams tt ON t.tournamentid = tt.tournamentid\n" +
            "LEFT JOIN\n" +
            "    tags tg ON t.tag_tagid = tg.tagid\n" +
            "WHERE\n" +
            "    t.tournamentid = :idTournament\n" +
            "GROUP BY\n" +
            "    t.tournamentid, p.path, tg.tagname\n" +
            "ORDER BY\n" +
            "    t.startdate;\n")
    Tuple getTournamentById(@Param("idTournament") int idTournament);

    default TournamentGetInfo getTournamentInfoById(int idTournament) {
        Tuple tuple = getTournamentById(idTournament);
//        if(tuple == null)
//            throw new IllegalArgumentException("Tournament not found");

        return new TournamentGetInfo(
                tuple.get("tournamentId", Integer.class),
                tuple.get("tournamentName", String.class),
                tuple.get("photoUrl", String.class),
                tuple.get("startDate", Date.class),
                tuple.get("endDate", Date.class),
                tuple.get("countTeam", Long.class),
                tuple.get("status", String.class),
                tuple.get("tagname", String.class));
    }


    @Query(nativeQuery = true, value = "SELECT n.newsid, n.title, n.content, n.publicationdate, n.author, p.path \n" +
            "FROM public.news n \n" +
            "LEFT JOIN public.photos p ON n.photo_id = p.photoid \n" +
            "LEFT JOIN public.tournaments t ON n.tag_tagid = t.tag_tagid \n" +
            "WHERE t.tournamentid = :tournamentId")
    List<Tuple> getTournamentNews(int tournamentId);

    default List<TournamentNews> getTournamentAllNews(int tournamentId) {
        List<Tuple> tuples = getTournamentNews(tournamentId);

        return tuples.stream()
                .map(tuple -> new TournamentNews(
                        tuple.get("newsid", Integer.class),
                        tuple.get("title", String.class),
                        tuple.get("content", String.class),
                        tuple.get("publicationdate", Date.class),
                        tuple.get("author", String.class),
                        tuple.get("path", String.class)))
                .collect(Collectors.toList());
    }

    @Query(nativeQuery = true, value = "SELECT p.photoid, p.path AS patch\n" +
            "FROM public.photos p\n" +
            "LEFT JOIN public.news n ON p.photoid = n.photo_id\n" +
            "WHERE p.tagid = (SELECT tag_tagid FROM public.tournaments WHERE tournamentid = ?)\n" +
            "AND n.photo_id IS NULL;\n")
    List<Tuple> getPhotosByTournamentId(@Param("tournamentId") Integer tournamentId);

    default List<TournamentPhoto> getTournamentAllPhoto(int tournamentId) {
        List<Tuple> tuples = getPhotosByTournamentId(tournamentId);

        return tuples.stream()
                .map(tuple -> new TournamentPhoto(
                        tuple.get("photoId", Integer.class),
                        tuple.get("patch", String.class)))
                .collect(Collectors.toList());
    }

    @Query(nativeQuery = true, value = "SELECT\n" +
            "    t.teamid,\n" +
            "    t.teamname,\n" +
            "    COUNT(m.matchid) AS totalMatchesPlayed,\n" +
            "    SUM(CASE WHEN m.team1id = t.teamid AND m.team1totalgoals > m.team2totalgoals THEN 1\n" +
            "             WHEN m.team2id = t.teamid AND m.team2totalgoals > m.team1totalgoals THEN 1 ELSE 0 END) AS wins,\n" +
            "    SUM(CASE WHEN m.team1id = t.teamid AND m.team1totalgoals = m.team2totalgoals THEN 1\n" +
            "             WHEN m.team2id = t.teamid AND m.team2totalgoals = m.team1totalgoals THEN 1 ELSE 0 END) AS draws,\n" +
            "    SUM(CASE WHEN m.team1id = t.teamid AND m.team1totalgoals < m.team2totalgoals THEN 1\n" +
            "             WHEN m.team2id = t.teamid AND m.team2totalgoals < m.team1totalgoals THEN 1 ELSE 0 END) AS losses,\n" +
            "    SUM(CASE WHEN m.team1id = t.teamid THEN m.team1totalgoals ELSE m.team2totalgoals END) AS goalsScored,\n" +
            "    SUM(CASE WHEN m.team1id = t.teamid THEN m.team2totalgoals ELSE m.team1totalgoals END) AS goalsConceded,\n" +
            "    tbl.points AS points,\n" +
            "    jsonb_build_object(\n" +
            "    'wonMatches', ARRAY_AGG(CASE WHEN m.team1id = t.teamid AND m.team1totalgoals > m.team2totalgoals THEN m.matchid\n" +
            "                                   WHEN m.team2id = t.teamid AND m.team2totalgoals > m.team1totalgoals THEN m.matchid END),\n" +
            "    'drawMatches', ARRAY_AGG(CASE WHEN m.team1totalgoals = m.team2totalgoals AND (m.team1id = t.teamid OR m.team2id = t.teamid) THEN m.matchid END),\n" +
            "    'lostMatches', ARRAY_AGG(CASE WHEN m.team1id = t.teamid AND m.team1totalgoals < m.team2totalgoals THEN m.matchid\n" +
            "                                    WHEN m.team2id = t.teamid AND m.team2totalgoals < m.team1totalgoals THEN m.matchid END)\n" +
            ") AS match_ids\n" +
            "FROM\n" +
            "    public.tournamentteams tt\n" +
            "JOIN\n" +
            "    public.footballteams t ON tt.teamid = t.teamid\n" +
            "LEFT JOIN\n" +
            "    public.matches m ON (t.teamid = m.team1id OR t.teamid = m.team2id) AND m.tournamentid = tt.tournamentid\n" +
            "LEFT JOIN\n" +
            "    public.tournamenttable tbl ON tt.teamid = tbl.teamid AND tt.tournamentid = tbl.tournamentid\n" +
            "WHERE\n" +
            "    tt.tournamentid = :tournamentId\n" +
            "GROUP BY\n" +
            "    t.teamid, t.teamname, tbl.points\n" +
            "ORDER BY\n" +
            "    t.teamid;")
    List<Tuple> getTableTournamentById(@Param("tournamentId") Integer tournamentId);

    default List<TournamentTeamStatsDTO> getTableTournamentByTournamentId(int tournamentId) {
        List<Tuple> tuples = getTableTournamentById(tournamentId);
        ObjectMapper objectMapper = new ObjectMapper();

        Map<Integer, String> matchIdsJsonMap = new HashMap<>();

        // Отримуємо та зберігаємо значення match_ids для кожного matchId в Map
        tuples.forEach(tuple -> {
            Integer matchId = tuple.get("teamid", Integer.class);
            String matchIdsJson = tuple.get("match_ids", String.class);
            matchIdsJsonMap.put(matchId, matchIdsJson);
        });

        return tuples.stream()
                .map(tuple -> {
                    Integer matchId = tuple.get("teamid", Integer.class);
                    String matchIdsJson = matchIdsJsonMap.get(matchId);

                    try {
                        // Парсимо рядок JSON у Map
                        Map<String, Integer[]> matchIdsMap = objectMapper.readValue(matchIdsJson, new TypeReference<>() {});

                        // Тепер можна використовувати matchIdsMap за необхідністю

                        // Отримуємо решту даних з tuples і повертаємо їх як результат
                        return new TournamentTeamStatsDTO(
                                tuple.get("teamId", Integer.class),
                                tuple.get("teamName", String.class),
                                tuple.get("totalMatchesPlayed", Long.class),
                                tuple.get("wins", Long.class),
                                tuple.get("draws", Long.class),
                                tuple.get("losses", Long.class),
                                tuple.get("goalsScored", Long.class),
                                tuple.get("goalsConceded", Long.class),
                                tuple.get("points", Integer.class),
                                matchIdsMap);
                    } catch (JsonProcessingException e) {
                        // Обробляйте виняток відповідно до ваших потреб
                        e.printStackTrace();
                        return null;
                    }
                })
                .collect(Collectors.toList());
    }

}
