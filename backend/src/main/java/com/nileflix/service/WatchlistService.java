package com.nileflix.service;

import com.nileflix.model.Movie;
import com.nileflix.model.Watchlist;
import com.nileflix.repository.MovieRepository;
import com.nileflix.repository.WatchlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WatchlistService {

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private MovieRepository movieRepository;

    public List<Watchlist> getWatchlist() {
        return watchlistRepository.findAll();
    }

    public Watchlist addToWatchlist(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Movie not found"));
        Watchlist watchlist = new Watchlist();
        watchlist.setMovie(movie);
        return watchlistRepository.save(watchlist);
    }

    public void removeFromWatchlist(Long movieId) {
        watchlistRepository.deleteByMovie_MovieId(movieId);
    }
}
