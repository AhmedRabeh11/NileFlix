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
public class TMDBVideoResponse {

    @JsonProperty("id")
    private Long movieId;

    @JsonProperty("results")
    private List<Video> results;

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public List<Video> getResults() {
        return results;
    }

    public void setResults(List<Video> results) {
        this.results = results;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class Video {
        @JsonProperty("key")
        private String key;

        @JsonProperty("site")
        private String site;

        @JsonProperty("type")
        private String type;
    }
}

