package com.nileflix.controller;

import com.nileflix.model.Watchlist;
import com.nileflix.service.WatchlistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
        Watchlist watchlist = new Watchlist();
        watchlist.setId(1L);

        when(watchlistService.getWatchlist()).thenReturn(List.of(watchlist));

        mockMvc.perform(get("/api/watchlist"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void testAddToWatchlist() throws Exception {
        Long movieId = 1L;
        Watchlist watchlist = new Watchlist();
        watchlist.setId(1L);

        when(watchlistService.addToWatchlist(movieId)).thenReturn(watchlist);

        mockMvc.perform(post("/api/watchlist/{movieId}", movieId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testRemoveFromWatchlist() throws Exception {
        Long movieId = 1L;

        doNothing().when(watchlistService).removeFromWatchlist(movieId);

        mockMvc.perform(delete("/api/watchlist/{movieId}", movieId))
                .andExpect(status().isOk());
    }
}
