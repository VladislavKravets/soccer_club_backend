package com.example.soccer_club_backend.repository;

import com.example.soccer_club_backend.dtos.footballTeam.FootballTeamInfo;
import com.example.soccer_club_backend.dtos.footballTeam.FootballTeams;
import com.example.soccer_club_backend.dtos.footballTeam.MatchInfo;
import com.example.soccer_club_backend.dtos.player.PlayerDTO;
import com.example.soccer_club_backend.models.FootballTeam;
import com.example.soccer_club_backend.models.Player;
import com.example.soccer_club_backend.models.TournamentTeam;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface FootballTeamRepository extends JpaRepository<FootballTeam, Integer> {

    FootballTeam findByTeamName(String teamName);
    FootballTeam findByTeamId(int footballTeamId);

    @Query(nativeQuery = true, value = "SELECT \n" +
            "    ft.teamid AS \"teamId\",\n" +
            "    ft.teamname AS \"teamName\",\n" +
            "    ft.coach,\n" +
            "    ft.district,\n" +
            "    t.tagname AS \"tagName\",\n" +
            "    p.path AS \"photoUrl\"\n" +
            "FROM \n" +
            "    public.footballteams ft\n" +
            "LEFT JOIN \n" +
            "    public.tags t ON ft.tag_tagid = t.tagid\n" +
            "LEFT JOIN \n" +
            "    public.photos p ON ft.photo_id = p.photoid;\n")
    List<Tuple> gAllFootballTeams();

    default List<FootballTeams> getAllFootballTeams() {
        List<Tuple> tuples = gAllFootballTeams();
        List<FootballTeams> footballTeams = new ArrayList<>();

        for (Tuple tuple : tuples) {
            Integer teamId = tuple.get("teamId", Integer.class);
            String teamName = tuple.get("teamName", String.class);
            String coach = tuple.get("coach", String.class);
            String district = tuple.get("district", String.class);
            String tagName = tuple.get("tagName", String.class);
            String photoUrl = tuple.get("photoUrl", String.class);

            FootballTeams footballTeam = new FootballTeams(teamId, teamName, coach, district, tagName, photoUrl);
            footballTeams.add(footballTeam);
        }

        return footballTeams;
    }

    @Query(nativeQuery = true, value = "SELECT\n" +
            "    teamname AS TeamName,\n" +
            "    COALESCE(COUNT(matches.matchid), 0) AS GamesPlayed,\n" +
            "    COALESCE(SUM(CASE WHEN team1id = teamid AND team1totalgoals > team2totalgoals THEN 1\n" +
            "                     WHEN team2id = teamid AND team2totalgoals > team1totalgoals THEN 1\n" +
            "                     ELSE 0 END), 0) AS Wins,\n" +
            "    COALESCE(SUM(CASE WHEN team1id = teamid THEN team1totalgoals\n" +
            "                     WHEN team2id = teamid THEN team2totalgoals\n" +
            "                     ELSE 0 END), 0) AS Goals,\n" +
            "    COALESCE(COUNT(DISTINCT matches.tournamentid), 0) AS Tournaments,\n" +
            "    jsonb_build_object(\n" +
            "    'wonMatches', COALESCE(ARRAY_AGG(CASE WHEN matches.team1id = footballteams.teamid AND matches.team1totalgoals > matches.team2totalgoals THEN matches.matchid\n" +
            "                                           WHEN matches.team2id = footballteams.teamid AND matches.team2totalgoals > matches.team1totalgoals THEN matches.matchid END ORDER BY matches.matchid DESC), '{}'),\n" +
            "    'drawMatches', COALESCE(ARRAY_AGG(CASE WHEN matches.team1totalgoals = matches.team2totalgoals AND (matches.team1id = footballteams.teamid OR matches.team2id = footballteams.teamid) THEN matches.matchid END ORDER BY matches.matchid DESC), '{}'),\n" +
            "    'lostMatches', COALESCE(ARRAY_AGG(CASE WHEN matches.team1id = footballteams.teamid AND matches.team1totalgoals < matches.team2totalgoals THEN matches.matchid\n" +
            "                                            WHEN matches.team2id = footballteams.teamid AND matches.team2totalgoals < matches.team1totalgoals THEN matches.matchid END ORDER BY matches.matchid DESC), '{}')\n" +
            ") AS match_ids,\n" +
            "    (SELECT COALESCE(path, '') FROM photos WHERE photoid = footballteams.photo_id) AS TeamPhotoURL\n" +
            "FROM\n" +
            "    public.footballteams\n" +
            "LEFT JOIN\n" +
            "    public.matches ON footballteams.teamid IN (matches.team1id, matches.team2id)\n" +
            "WHERE footballteams.teamid = :teamId\n" +
            "GROUP BY teamname, footballteams.photo_id\n" +
            "ORDER BY GamesPlayed DESC;\n")
    Tuple gInfoByTeamId(int teamId);

    default FootballTeamInfo getInfoByTeamId(int teamId) throws JsonProcessingException {
        Tuple tuple = gInfoByTeamId(teamId);

        String teamName = tuple.get("TeamName", String.class);
        Integer gamesPlayed = tuple.get("GamesPlayed", Long.class).intValue();
        Integer wins = tuple.get("Wins", Long.class).intValue();
        Integer goals = tuple.get("Goals", Long.class).intValue();
        Integer tournamentsPlayed = tuple.get("Tournaments", Long.class).intValue();
        String path = tuple.get("TeamPhotoURL", String.class);

        // Отримання значень для "Last 5 Matches"
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, List<Integer>> last5MatchesMap = objectMapper.readValue(tuple.get("match_ids", String.class), new TypeReference<>() {});

        // Створення та повернення об'єкта FootballTeamInfo
        return new FootballTeamInfo(teamName, gamesPlayed, wins, goals, tournamentsPlayed, path, last5MatchesMap);
    }

    @Query(nativeQuery = true, value = "SELECT \n" +
            "    TO_CHAR(m.matchdate, 'DD Mon. YYYY') AS date,\n" +
            "\tmatchtime,\n" +
            "    m.team1id AS team1_link,\n" +
            "    m.team2id AS team2_link,\n" +
            "    m.matchid AS match_link,\n" +
            "    t1.teamname AS nameTeam1,\n" +
            "    t2.teamname AS nameTeam2,\n" +
            "    CASE\n" +
            "        WHEN m.team1totalgoals > m.team2totalgoals THEN 'win'\n" +
            "        WHEN m.team1totalgoals < m.team2totalgoals THEN 'lose'\n" +
            "        ELSE 'draw'\n" +
            "    END AS win,\n" +
            "\t\tCONCAT(m.team1totalgoals, ':', m.team2totalgoals) AS score\n" +
            "FROM matches m\n" +
            "JOIN footballteams t1 ON m.team1id = t1.teamid\n" +
            "JOIN footballteams t2 ON m.team2id = t2.teamid\n" +
            "WHERE m.team1id = :teamId OR m.team2id = :teamId\n" +
            "ORDER BY m.matchdate;\n")
    List<Tuple> gMatchesByTeamId(int teamId);

    default List<MatchInfo> getMatchesByTeamId(int teamId) {
        List<Tuple> tuples = gMatchesByTeamId(teamId);
        List<MatchInfo> matchInfos = new ArrayList<>();

        for (Tuple tuple : tuples) {
            String date = tuple.get("date", String.class);
            Time matchTime = tuple.get("matchtime", Time.class);
            Integer team1Link = tuple.get("team1_link", Integer.class);
            Integer team2Link = tuple.get("team2_link", Integer.class);
            Integer matchLink = tuple.get("match_link", Integer.class);
            String nameTeam1 = tuple.get("nameTeam1", String.class);
            String nameTeam2 = tuple.get("nameTeam2", String.class);
            String win = tuple.get("win", String.class);
            String score = tuple.get("score", String.class);

            MatchInfo matchInfo = new MatchInfo(date, matchTime, team1Link, team2Link, matchLink, nameTeam1, nameTeam2, score, win);
            matchInfos.add(matchInfo);
        }

        return matchInfos;
    }

}

