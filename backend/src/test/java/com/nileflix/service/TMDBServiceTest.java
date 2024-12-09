package com.nileflix.service;

import com.nileflix.dto.*;
import com.nileflix.model.*;
import com.nileflix.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TMDBServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private ActorRepository actorRepository;

    @Mock
    private TrailerRepository trailerRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private TMDBService tmdbService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFetchAndStoreEgyptianMovies() {
        // Mock the response from the TMDB API
        TMDBMovieResponse movieResponse = new TMDBMovieResponse();
        movieResponse.setResults(List.of(TMDBMovieResponse.MovieData.builder().id(1L).title("Movie Title").build()));
        movieResponse.setTotalPages(1);

        when(restTemplate.getForObject(anyString(), eq(TMDBMovieResponse.class))).thenReturn(movieResponse);

        // Call the method
        String result = tmdbService.fetchAndStoreEgyptianMovies();

        // Verify the interactions and assertions
        assertEquals("Movies fetched and stored successfully", result);
        verify(movieRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testFetchAndStoreActors() {
        Long tmdbId = 1L;
        TMDBCreditsResponse creditsResponse = new TMDBCreditsResponse();
        creditsResponse.setCast(List.of(TMDBCreditsResponse.CastMember.builder().id(1L).name("Actor Name").build()));

        when(restTemplate.getForObject(anyString(), eq(TMDBCreditsResponse.class))).thenReturn(creditsResponse);

        // Call the method
        List<Actor> actors = tmdbService.fetchAndStoreActors(tmdbId);

        // Verify the interactions and assertions
        assertNotNull(actors);
        assertEquals(1, actors.size());
        assertEquals("Actor Name", actors.get(0).getName());
        verify(actorRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testFetchAndStoreTrailers() {
        Long tmdbId = 1L;
        Movie movie = new Movie();
        movie.setTmdbId(tmdbId);
        TMDBVideoResponse videoResponse = new TMDBVideoResponse();
        videoResponse.setResults(List.of(TMDBVideoResponse.Video.builder().key("trailer_key").type("Trailer").build()));

        when(restTemplate.getForObject(anyString(), eq(TMDBVideoResponse.class))).thenReturn(videoResponse);

        // Call the method
        List<Trailer> trailers = tmdbService.fetchAndStoreTrailers(tmdbId, movie);

        // Verify the interactions and assertions
        assertNotNull(trailers);
        assertEquals(1, trailers.size());
        assertEquals("trailer_key", trailers.get(0).getKey());
        verify(trailerRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testFetchGenres() throws Exception {
        TMDBGenreResponse genreResponse = new TMDBGenreResponse();
        genreResponse.setGenres(List.of(TMDBGenreResponse.Genre.builder().id(1).name("Action").build()));

        when(restTemplate.getForObject(anyString(), eq(TMDBGenreResponse.class))).thenReturn(genreResponse);

        // Use reflection to access the private method
        Method fetchGenresMethod = TMDBService.class.getDeclaredMethod("fetchGenres");
        fetchGenresMethod.setAccessible(true);

        // Call the method
        Map<Integer, String> genres = (Map<Integer, String>) fetchGenresMethod.invoke(tmdbService);

        // Verify the interactions and assertions
        assertNotNull(genres);
        assertFalse(genres.isEmpty());
        assertEquals("Action", genres.get(1));
    }

    @Test
    void testFetchMovieRuntime() throws Exception {
        Long tmdbId = 1L;
        TMDBMovieResponse.MovieData movieData = new TMDBMovieResponse.MovieData();
        movieData.setRuntime(120);

        when(restTemplate.getForObject(anyString(), eq(TMDBMovieResponse.MovieData.class))).thenReturn(movieData);

        // Use reflection to access the private method
        Method fetchMovieRuntimeMethod = TMDBService.class.getDeclaredMethod("fetchMovieRuntime", Long.class);
        fetchMovieRuntimeMethod.setAccessible(true);

        // Call the method
        Integer runtime = (Integer) fetchMovieRuntimeMethod.invoke(tmdbService, tmdbId);

        // Verify the interactions and assertions
        assertNotNull(runtime);
        assertTrue(runtime > 0);
        assertEquals(120, runtime);
    }
}
