package com.nileflix.controller;

import com.nileflix.model.Watchlist;
import com.nileflix.service.WatchlistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class WatchlistControllerTest {

    @Mock
    private WatchlistService watchlistService;

    @InjectMocks
    private WatchlistController watchlistController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(watchlistController).build();
    }

    @Test
    void testGetWatchlist() throws Exception {
        String userId = "user123";
        Watchlist watchlist = new Watchlist();
        watchlist.setId(1L);
        watchlist.setUserId(userId);

        when(watchlistService.getWatchlistByUserId(userId)).thenReturn(List.of(watchlist));

        mockMvc.perform(get("/api/watchlist/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(userId));
    }

    @Test
    void testAddToWatchlist() throws Exception {
        String userId = "user123";
        Long movieId = 1L;
        Watchlist watchlist = new Watchlist();
        watchlist.setId(1L);
        watchlist.setUserId(userId);

        when(watchlistService.addToWatchlist(userId, movieId)).thenReturn(watchlist);

        mockMvc.perform(post("/api/watchlist/{userId}/{movieId}", userId, movieId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userId));
    }

    @Test
    void testRemoveFromWatchlist() throws Exception {
        String userId = "user123";
        Long movieId = 1L;

        doNothing().when(watchlistService).removeFromWatchlist(userId, movieId);

        mockMvc.perform(delete("/api/watchlist/{userId}/{movieId}", userId, movieId))
                .andExpect(status().isOk());
    }
}
