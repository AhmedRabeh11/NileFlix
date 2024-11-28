package com.nileflix.service;

import com.nileflix.dto.TMDBMovieResponse;
import com.nileflix.model.Movie;
import com.nileflix.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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
                /*movie.setReleaseDate(movieData.getReleaseDate() != null
                        ? java.sql.Date.valueOf(movieData.getReleaseDate())
                        : new java.sql.Date(System.currentTimeMillis())); // Default to today's date*/

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
                return movie;
            }).collect(Collectors.toList());

            movieRepository.saveAll(movies);
            return "Movies fetched from TMDB API and stored in the database.";
        }

        return "No movies fetched from TMDB API.";
    }
}
