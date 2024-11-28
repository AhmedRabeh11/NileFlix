package com.nileflix.service;

import com.nileflix.dto.*;
import com.nileflix.model.*;
import com.nileflix.repository.*;
import org.springframework.beans.factory.annotation.*;
*import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
public class TMDBService {

    @Value("${tmdb.api.key}")
    private String apiKey;

    @Value("${tmdb.api.url}")
    private String apiUrl;

    @Autowired
    private MovieRepository movieRepository;


    public List<Actor> fetchAndStoreActors(Long tmdbId) {
        String url = apiUrl + "/movie/" + tmdbId + "/credits?api_key=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        TMDBCreditsResponse response = restTemplate.getForObject(url, TMDBCreditsResponse.class);

        if (response != null && response.getCast() != null) {
            List<Actor> actors = response.getCast().stream().map(actorData -> {
                Actor actor = new Actor();
                actor.setTmdbId(actorData.getId());
                actor.setName(actorData.getName());
                actor.setPhoto(actorData.getProfilePath() != null
                        ? "https://image.tmdb.org/t/p/w500" + actorData.getProfilePath()
                        : null);
                actor.setPopularity(actorData.getPopularity());
                return actor;
            }).collect(Collectors.toList());

            // Save actors and return the list
            return actors;
        }

        return List.of(); // Return empty list if no cast is found
    }


    public String fetchAndStoreEgyptianMovies() {
        if (movieRepository.count() > 0) {
            return "Movies already exist in the database. Fetch skipped.";
        }

        String url = apiUrl + "/discover/movie?api_key=" + apiKey + "&with_original_language=ar";

        RestTemplate restTemplate = new RestTemplate();
        TMDBMovieResponse response = restTemplate.getForObject(url, TMDBMovieResponse.class);

        if (response != null && response.getResults() != null) {
            List<Movie> movies = response.getResults().stream().map(movieData -> {
                Movie movie = new Movie();
                movie.setTmdbId(movieData.getId());
                movie.setTitle(movieData.getTitle());
                movie.setOverview(movieData.getOverview());
                if (movieData.getReleaseDate() != null && !movieData.getReleaseDate().isEmpty()) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        movie.setReleaseDate(new java.sql.Date(sdf.parse(movieData.getReleaseDate()).getTime()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                        movie.setReleaseDate(new java.sql.Date(System.currentTimeMillis())); // Fallback to today's date
                    }
                } else {
                    movie.setReleaseDate(new java.sql.Date(System.currentTimeMillis())); // Default to today's date
                }
                movie.setRating(movieData.getRating() != null
                        ? BigDecimal.valueOf(movieData.getRating())
                        : BigDecimal.ZERO);
                movie.setPosterImage(movieData.getPosterPath() != null
                        ? "https://image.tmdb.org/t/p/w500" + movieData.getPosterPath()
                        : null);
                movie.setBackdropImage(movieData.getBackdropPath() != null
                        ? "https://image.tmdb.org/t/p/w1280" + movieData.getBackdropPath()
                        : null);
                movie.setPopularity(movieData.getPopularity());
                movie.setGenres(movieData.getGenreIds() != null
                        ? movieData.getGenreIds().stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(", "))
                        : null);

                // Fetch and set actors for this movie
                List<Actor> actors = fetchAndStoreActors(movie.getTmdbId());
                movie.setActors(actors);

                return movie;
            }).collect(Collectors.toList());

            movieRepository.saveAll(movies);
            return "Movies fetched from TMDB API and stored in the database.";
        }

        return "No movies fetched from TMDB API.";
    }
}
