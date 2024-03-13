package com.example.soccer_club_backend.repository;

import com.example.soccer_club_backend.dtos.tournament.TournamentPhoto;
import com.example.soccer_club_backend.dtos.tournamentteam.TournamentTeamByTournamentId;
import com.example.soccer_club_backend.dtos.tournamentteam.TournamentTeamDTO;
import com.example.soccer_club_backend.models.Card;
import com.example.soccer_club_backend.models.TournamentTeam;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface TournamentTeamRepository extends JpaRepository<TournamentTeam, Integer> {
    List<TournamentTeam> findByTournamentId(long teamId);

    @Transactional
    @Modifying
    @Query("DELETE FROM TournamentTeam t WHERE t.tournamentId = :tournamentId and t.teamId = :teamId")
    void deleteByTournamentIdAndTeamId(@Param("tournamentId") int tournamentId, @Param("teamId") int teamId);

    @Query(nativeQuery = true, value = "SELECT ft.teamid, ft.teamname\n" +
            "FROM footballteams ft\n" +
            "LEFT JOIN tournamentteams tt ON ft.teamid = tt.teamid\n" +
            "WHERE " +
//            "tt.teamid IS NULL\n" +
//            "  AND " +
            "ft.teamid NOT IN (\n" +
            "    SELECT tt.teamid\n" +
            "    FROM tournamentteams tt\n" +
            "    WHERE tt.tournamentid = :tournamentId\n" +
            "  );\n")
    List<Tuple> getTournamentTeamByNotTeams(@Param("tournamentId") Integer tournamentId);

    default List<TournamentTeamDTO> getTournamentTeamsByNotTeams(int tournamentId) {
        List<Tuple> tuples = getTournamentTeamByNotTeams(tournamentId);

        return tuples.stream()
                .map(tuple -> new TournamentTeamDTO(
                        tuple.get("teamid", Integer.class),
                        tuple.get("teamname", String.class)))
                .collect(Collectors.toList());
    }

    @Query(nativeQuery = true, value = "SELECT ft.teamid, tt.teamname\n" +
            "FROM tournamentteams ft\n" +
            "join footballteams tt ON ft.teamid = tt.teamid\n" +
            "where tournamentid = :tournamentId")
    List<Tuple> getTournamentTeamByTeams(@Param("tournamentId") Integer tournamentId);

    default List<TournamentTeamDTO> getTournamentTeamsByTeams(int tournamentId) {
        List<Tuple> tuples = getTournamentTeamByTeams(tournamentId);

        return tuples.stream()
                .map(tuple -> new TournamentTeamDTO(
                        tuple.get("teamid", Integer.class),
                        tuple.get("teamname", String.class)))
                .collect(Collectors.toList());
    }

    @Query(nativeQuery = true, value = "SELECT\n" +
            "    ft.teamid,\n" +
            "    ft.teamname,\n" +
            "    p.path AS photo_url\n" +
            "FROM\n" +
            "    public.tournamentteams tt\n" +
            "JOIN\n" +
            "    public.footballteams ft ON tt.teamid = ft.teamid\n" +
            "JOIN\n" +
            "    public.photos p ON ft.photo_id = p.photoid\n" +
            "WHERE\n" +
            "    tt.tournamentid = :tournamentId")
    List<Tuple> gFootballTeamByTournamentId(int tournamentId);

    default List<TournamentTeamByTournamentId> getFootballTeamByTournamentId(int tournamentId) {
        List<Tuple> tuples = gFootballTeamByTournamentId(tournamentId);

        return tuples.stream()
                .map(tuple -> new TournamentTeamByTournamentId(
                        tuple.get("teamid", Integer.class),
                        tuple.get("teamname", String.class),
                        tuple.get("photo_url", String.class)))
                .collect(Collectors.toList());
    }


}
