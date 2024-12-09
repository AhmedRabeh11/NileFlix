package com.nileflix.service;

import com.nileflix.model.Movie;
import com.nileflix.model.Watchlist;
import com.nileflix.repository.MovieRepository;
import com.nileflix.repository.WatchlistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WatchlistServiceTest {

    @Mock
    private WatchlistRepository watchlistRepository;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private WatchlistService watchlistService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetWatchlistByUserId() {
        String userId = "user123";
        Watchlist watchlist = new Watchlist();
        watchlist.setId(1L);
        watchlist.setUserId(userId);

        when(watchlistRepository.findByUserId(userId)).thenReturn(List.of(watchlist));

        List<Watchlist> watchlists = watchlistService.getWatchlistByUserId(userId);

        assertNotNull(watchlists);
        assertEquals(1, watchlists.size());
        assertEquals(userId, watchlists.get(0).getUserId());
    }

    @Test
    void testAddToWatchlist() {
        String userId = "user123";
        Long movieId = 1L;
        Movie movie = new Movie();
        movie.setMovieId(movieId);

        Watchlist watchlist = new Watchlist();
        watchlist.setId(1L);
        watchlist.setUserId(userId);
        watchlist.setMovie(movie);

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        when(watchlistRepository.save(any(Watchlist.class))).thenReturn(watchlist);

        Watchlist savedWatchlist = watchlistService.addToWatchlist(userId, movieId);

        assertNotNull(savedWatchlist);
        assertEquals(userId, savedWatchlist.getUserId());
        assertEquals(movieId, savedWatchlist.getMovie().getMovieId());
    }

    @Test
    void testRemoveFromWatchlist() {
        String userId = "user123";
        Long movieId = 1L;

        doNothing().when(watchlistRepository).deleteByUserIdAndMovie_MovieId(userId, movieId);

        assertDoesNotThrow(() -> watchlistService.removeFromWatchlist(userId, movieId));
    }
}
