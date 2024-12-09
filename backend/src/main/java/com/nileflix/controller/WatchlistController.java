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

    @GetMapping
    public List<Watchlist> getWatchlist() {
        return watchlistService.getWatchlist();
    }

    @PostMapping("/{movieId}")
    public Watchlist addToWatchlist(@PathVariable Long movieId) {
        return watchlistService.addToWatchlist(movieId);
    }

    @DeleteMapping("/{movieId}")
    public void removeFromWatchlist(@PathVariable Long movieId) {
        watchlistService.removeFromWatchlist(movieId);
    }
}
