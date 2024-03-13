package com.example.soccer_club_backend;

import com.example.soccer_club_backend.controllers.TournamentController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(TournamentController.class)
@AutoConfigureMockMvc
public class TournamentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllTournament() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tournament"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetTournamentInfoById() throws Exception {
        int tournamentId = 1; // Replace with a valid tournament ID
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tournament/getTournamentInfoById/{tournamentId}", tournamentId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
