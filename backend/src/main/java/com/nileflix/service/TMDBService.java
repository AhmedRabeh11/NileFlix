package com.nileflix.service;

import com.nileflix.dto.*;
import com.nileflix.model.*;
import com.nileflix.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TMDBService {

    @Value("${tmdb.api.key}")
    private String apiKey;

    @Value("${tmdb.api.url}")
    private String apiUrl;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private TrailerRepository trailerRepository;

    private static final List<String> DIRECTOR_IDS = List.of("1115944", "1186523", "2002988", "226425", "2613122", "4346111", "4645733", "2340131", "1449878", "224916", "224915", "224914", "224913", "224912", "224911", "224910", "224909", "224908", "224907", "224906", "224905", "224904", "224903", "224902", "224901", "224900", "224899", "224898", "224897", "224896", "224895", "224894", "224893", "224892", "224891", "224890", "224889", "224888", "224887", "224886", "224885", "224884", "224883", "224882", "224881", "224880", "224879", "224878", "224877", "224876", "224875", "224874", "224873", "224872", "224871", "224870", "224869", "224868", "224867", "224866", "224865", "224864", "224863", "224862", "224861", "224860", "224859", "224858", "224857", "224856", "224855", "224854", "224853", "224852", "224851", "224850", "224849", "224848", "224847", "224846", "224845", "224844", "224843", "224842", "224841", "224840", "224839", "224838", "224837", "224836", "224835", "224834", "224833", "224832", "224831", "224830", "224829", "224828", "224827", "224826", "224825", "224824", "224823", "224822", "224821", "224820", "224819", "224818", "224817", "224816", "224815", "224814", "224813", "224812", "224811", "224810", "224809", "224808", "224807", "224806", "224805", "224804", "224803", "224802", "224801", "224800", "224799", "224798", "224797", "224796", "224795", "224794", "224793", "224792", "224791");

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

                // Fetch actor details to get the biography
                String actorDetailsUrl = apiUrl + "/person/" + actorData.getId() + "?api_key=" + apiKey;
                TMDBCreditsResponse.CastMember actorDetailsResponse = restTemplate.getForObject(actorDetailsUrl, TMDBCreditsResponse.CastMember.class);
                if (actorDetailsResponse != null) {
                    actor.setBiography(actorDetailsResponse.getBiography());
                }

                return actor;
            }).collect(Collectors.toList());

            // Save actors and return the list
            actorRepository.saveAll(actors);
            return actors;
        }

        return List.of(); // Return empty list if no cast is found
    }

    public List<Trailer> fetchAndStoreTrailers(Long tmdbId, Movie movie) {
        String url = apiUrl + "/movie/" + tmdbId + "/videos?api_key=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        TMDBVideoResponse response = restTemplate.getForObject(url, TMDBVideoResponse.class);

        if (response != null && response.getResults() != null) {
            List<Trailer> trailers = response.getResults().stream()
                    .filter(video -> "Trailer".equalsIgnoreCase(video.getType())) // Only fetch trailers
                    .map(videoData -> {
                        Trailer trailer = new Trailer();
                        trailer.setKey(videoData.getKey());
                        trailer.setSite(videoData.getSite());
                        trailer.setType(videoData.getType());
                        trailer.setMovie(movie); // Associate trailer with movie
                        return trailer;
                    }).collect(Collectors.toList());

            // Save trailers and return the list
            trailerRepository.saveAll(trailers);
            return trailers;
        }

        return List.of(); // Return empty list if no trailers are found
    }

    public String fetchAndStoreEgyptianMovies() {
        if (movieRepository.count() > 0) {
            return "Movies already fetched and stored";
        }

        int page = 1;
        int totalPages = 1;

        RestTemplate restTemplate = new RestTemplate();

        // Fetch genres from TMDB
        Map<Integer, String> genreMap = fetchGenres();

        while (page <= totalPages) {
            for (String directorId : DIRECTOR_IDS) {
                String url = apiUrl + "/discover/movie?api_key=" + apiKey + "&language=en-US&with_original_language=ar&with_crew=" + directorId + "&sort_by=vote_average.desc&page=" + page;
                TMDBMovieResponse response = restTemplate.getForObject(url, TMDBMovieResponse.class);

                if (response != null && response.getResults() != null) {
                    totalPages = response.getTotalPages();
                    List<Movie> movies = response.getResults().stream().map(movieData -> {
                        Movie movie = new Movie();
                        movie.setTmdbId(movieData.getId());
                        movie.setTitle(movieData.getTitle());
                        movie.setOverview(movieData.getOverview());
                        movie.setReleaseDate(parseDate(movieData.getReleaseDate()));
                        movie.setRating(movieData.getRating() != null ? BigDecimal.valueOf(movieData.getRating()) : BigDecimal.ZERO);
                        movie.setGenres(convertGenreIdsToNames(movieData.getGenreIds(), genreMap));
                        movie.setPosterImage(movieData.getPosterPath() != null ? "https://image.tmdb.org/t/p/w500" + movieData.getPosterPath() : null);
                        movie.setBackdropImage(movieData.getBackdropPath() != null ? "https://image.tmdb.org/t/p/w1280" + movieData.getBackdropPath() : null);
                        movie.setPopularity(movieData.getPopularity());

                        // Fetch and store runtime
                        movie.setRuntime(fetchMovieRuntime(movie.getTmdbId()));

                        // Fetch and store actors and trailers for each movie
                        List<Actor> actors = fetchAndStoreActors(movie.getTmdbId());
                        movie.setActors(actors);

                        List<Trailer> trailers = fetchAndStoreTrailers(movie.getTmdbId(), movie);
                        movie.setTrailers(trailers);

                        return movie;
                    }).collect(Collectors.toList());

                    movieRepository.saveAll(movies);
                }
            }

            page++;
        }

        return "Movies fetched and stored successfully";
    }

    private Date parseDate(String dateStr) {
        if (dateStr != null && !dateStr.isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                return new java.sql.Date(sdf.parse(dateStr).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return new java.sql.Date(System.currentTimeMillis()); // Default to today's date
    }

    private String convertGenreIdsToNames(List<Integer> genreIds, Map<Integer, String> genreMap) {
        return genreIds.stream()
                .map(genreMap::get)
                .filter(genre -> genre != null && !genre.isEmpty())
                .collect(Collectors.joining(", "));
    }

    private Map<Integer, String> fetchGenres() {
        String url = apiUrl + "/genre/movie/list?api_key=" + apiKey + "&language=en-US";
        RestTemplate restTemplate = new RestTemplate();
        TMDBGenreResponse response = restTemplate.getForObject(url, TMDBGenreResponse.class);

        if (response != null && response.getGenres() != null) {
            return response.getGenres().stream()
                    .collect(Collectors.toMap(TMDBGenreResponse.Genre::getId, TMDBGenreResponse.Genre::getName));
        }

        return Map.of(); // Return empty map if no genres are found
    }

    private Integer fetchMovieRuntime(Long tmdbId) {
        String url = apiUrl + "/movie/" + tmdbId + "?api_key=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        TMDBMovieResponse.MovieData response = restTemplate.getForObject(url, TMDBMovieResponse.MovieData.class);

        if (response != null) {
            return response.getRuntime();
        }

        return null; // Return null if runtime is not found
    }
}
