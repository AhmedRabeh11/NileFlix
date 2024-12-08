package com.nileflix.controller;

import com.nileflix.model.Watchlist;
import com.nileflix.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/watchlist")
@CrossOrigin(origins = "*")
public class WatchlistController {

    @Autowired
    private WatchlistService watchlistService;

    @GetMapping("/{userId}")
    public List<Watchlist> getWatchlist(@PathVariable String userId) {
        return watchlistService.getWatchlistByUserId(userId);
    }

    @PostMapping("/{userId}/{movieId}")
    public Watchlist addToWatchlist(@PathVariable String userId, @PathVariable Long movieId) {
        return watchlistService.addToWatchlist(userId, movieId);
    }

    @DeleteMapping("/{userId}/{movieId}")
    public void removeFromWatchlist(@PathVariable String userId, @PathVariable Long movieId) {
        watchlistService.removeFromWatchlist(userId, movieId);
    }
}
