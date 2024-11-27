package com.nileflix.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TMDBMovieResponse {

    @JsonProperty("results")
    private List<MovieData> results;

    public static class MovieData {
        @JsonProperty("id")
        private Long id;

        @JsonProperty("title")
        private String title;

        @JsonProperty("overview")
        private String overview;

        @JsonProperty("release_date")
        private String releaseDate;

        @JsonProperty("genre_ids")
        private List<Integer> genreIds;

        @JsonProperty("vote_average")
        private Double rating;

        @JsonProperty("poster_path")
        private String posterPath;

    }

}
