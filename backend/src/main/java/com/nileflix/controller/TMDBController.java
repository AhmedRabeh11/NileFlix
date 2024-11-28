package com.nileflix.controller;

import com.nileflix.service.TMDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TMDBController {

    @Autowired
    private TMDBService tmdbService;

    @GetMapping("/api/movies/fetch")
    public String fetchMovies() {
        return tmdbService.fetchAndStoreEgyptianMovies();
    }
}

