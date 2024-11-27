package com.nileflix.service;

import com.nileflix.model.Movie;
import com.nileflix.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> getFeaturedMovies() {
        return movieRepository.findTop10ByOrderByRatingDesc();
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }
}


