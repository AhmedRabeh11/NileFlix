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
    void testGetWatchlist() {
        Watchlist watchlist = new Watchlist();
        watchlist.setId(1L);

        when(watchlistRepository.findAll()).thenReturn(List.of(watchlist));

        List<Watchlist> watchlists = watchlistService.getWatchlist();

        assertNotNull(watchlists);
        assertEquals(1, watchlists.size());
    }

    @Test
    void testAddToWatchlist() {
        Long movieId = 1L;
        Movie movie = new Movie();
        movie.setMovieId(movieId);

        Watchlist watchlist = new Watchlist();
        watchlist.setId(1L);
        watchlist.setMovie(movie);

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        when(watchlistRepository.save(any(Watchlist.class))).thenReturn(watchlist);

        Watchlist savedWatchlist = watchlistService.addToWatchlist(movieId);

        assertNotNull(savedWatchlist);
        assertEquals(movieId, savedWatchlist.getMovie().getMovieId());
    }

    @Test
    void testRemoveFromWatchlist() {
        Long movieId = 1L;

        doNothing().when(watchlistRepository).deleteByMovie_MovieId(movieId);

        assertDoesNotThrow(() -> watchlistService.removeFromWatchlist(movieId));
    }
}
