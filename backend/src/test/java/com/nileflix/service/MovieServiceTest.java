package com.nileflix.service;

import com.nileflix.model.Movie;
import com.nileflix.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetFeaturedMovies() {
        Movie movie = new Movie();
        movie.setMovieId(1L);
        movie.setTitle("Featured Movie");

        when(movieRepository.findTop10ByOrderByRatingDesc()).thenReturn(List.of(movie));

        List<Movie> movies = movieService.getFeaturedMovies();

        assertNotNull(movies);
        assertEquals(1, movies.size());
        assertEquals("Featured Movie", movies.get(0).getTitle());
    }

    @Test
    void testGetAllMovies() {
        Movie movie = new Movie();
        movie.setMovieId(1L);
        movie.setTitle("All Movies");

        when(movieRepository.findAll()).thenReturn(List.of(movie));

        List<Movie> movies = movieService.getAllMovies();

        assertNotNull(movies);
        assertEquals(1, movies.size());
        assertEquals("All Movies", movies.get(0).getTitle());
    }

    @Test
    void testGetMovieById() {
        Long movieId = 1L;
        Movie movie = new Movie();
        movie.setMovieId(movieId);
        movie.setTitle("Movie by ID");

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        Optional<Movie> foundMovie = movieService.getMovieById(movieId);

        assertTrue(foundMovie.isPresent());
        assertEquals("Movie by ID", foundMovie.get().getTitle());
    }

    @Test
    void testGetMovieByIdNotFound() {
        Long movieId = 1L;

        when(movieRepository.findById(movieId)).thenReturn(Optional.empty());

        Optional<Movie> foundMovie = movieService.getMovieById(movieId);

        assertFalse(foundMovie.isPresent());
    }
}
