package com.example.soccer_club_backend.repository;

import com.example.soccer_club_backend.dtos.player.PlayerStatsDTO;
import com.example.soccer_club_backend.models.Player;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {
    @Query(nativeQuery = true, value = "SELECT\n" +
            "    p.playerid AS playerId,\n" +
            "    CONCAT(p.firstname, ' ', p.lastname) AS playerName,\n" +
            "    COUNT(DISTINCT m.matchid) AS totalMatchesPlayed,\n" +
            "    SUM(CASE WHEN g.description = 'Goal' THEN 1 ELSE 0 END) AS goals,\n" +
            "    SUM(CASE WHEN g.description = 'PenaltyGoal' THEN 1 ELSE 0 END) AS penaltyGoals,\n" +
            "    CASE\n" +
            "        WHEN COUNT(DISTINCT m.matchid) = 0 THEN 0\n" +
            "        ELSE CAST(SUM(CASE WHEN g.description = 'Goal' THEN 1 ELSE 0 END) AS DECIMAL) / COUNT(DISTINCT m.matchid)\n" +
            "    END AS averageGoalsPerGame,\n" +
            "    SUM(CASE WHEN c.cardtype = 'Yellow' THEN 1 ELSE 0 END) AS yellowCards,\n" +
            "    SUM(CASE WHEN c.cardtype = 'Red' THEN 1 ELSE 0 END) AS redCards\n" +
            "FROM\n" +
            "    public.players p\n" +
            "LEFT JOIN\n" +
            "    public.matches m ON p.teamid = m.team1id OR p.teamid = m.team2id\n" +
            "LEFT JOIN\n" +
            "    public.goals g ON p.playerid = g.playerid AND m.matchid = g.matchid\n" +
            "LEFT JOIN\n" +
            "    public.cards c ON p.playerid = c.playerid AND m.matchid = c.matchid\n" +
            "WHERE p.active = true\n" +
            "GROUP BY\n" +
            "    p.playerid, p.firstname, p.lastname\n" +
            "ORDER BY\n" +
            "    totalMatchesPlayed DESC;")
    List<Tuple> getPlayerStats();

    default List<PlayerStatsDTO> getPlayerStatsDTO() {
        List<Tuple> tuples = getPlayerStats();

        return tuples.stream()
                .map(tuple -> new PlayerStatsDTO(
                        tuple.get("playerid", Integer.class),
                        tuple.get("playername", String.class),
                        tuple.get("totalMatchesPlayed", Long.class),
                        tuple.get("goals", Long.class),
                        tuple.get("penaltyGoals", Long.class),
                        tuple.get("averageGoalsPerGame", BigDecimal.class),
                        tuple.get("yellowCards", Long.class),
                        tuple.get("redCards", Long.class)))
                .collect(Collectors.toList());
    }


}
