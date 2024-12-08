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

    public List<Watchlist> getWatchlistByUserId(String userId) {
        return watchlistRepository.findByUserId(userId);
    }

    public Watchlist addToWatchlist(String userId, Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Movie not found"));
        Watchlist watchlist = new Watchlist();
        watchlist.setUserId(userId);
        watchlist.setMovie(movie);
        return watchlistRepository.save(watchlist);
    }

    public void removeFromWatchlist(String userId, Long movieId) {
        watchlistRepository.deleteByUserIdAndMovie_MovieId(userId, movieId);
    }
}
