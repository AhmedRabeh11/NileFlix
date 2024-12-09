package com.nileflix.controller;

import com.nileflix.model.Movie;
import com.nileflix.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MovieControllerTest {

    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieController movieController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
    }

    @Test
    void testGetFeaturedMovies() throws Exception {
        Movie movie = new Movie();
        movie.setMovieId(1L);
        movie.setTitle("Featured Movie");

        when(movieService.getFeaturedMovies()).thenReturn(List.of(movie));

        mockMvc.perform(get("/api/movies/featured"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Featured Movie"));
    }

    @Test
    void testGetAllMovies() throws Exception {
        Movie movie = new Movie();
        movie.setMovieId(1L);
        movie.setTitle("All Movies");

        when(movieService.getAllMovies()).thenReturn(List.of(movie));

        mockMvc.perform(get("/api/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("All Movies"));
    }

    @Test
    void testGetMovieById() throws Exception {
        Long movieId = 1L;
        Movie movie = new Movie();
        movie.setMovieId(movieId);
        movie.setTitle("Movie by ID");

        when(movieService.getMovieById(movieId)).thenReturn(Optional.of(movie));

        mockMvc.perform(get("/api/movies/{id}", movieId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Movie by ID"));
    }
}
