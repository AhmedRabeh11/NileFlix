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
public class TMDBCreditsResponse {

    @JsonProperty("id")
    private Long movieId;

    @JsonProperty("cast")
    private List<CastMember> cast;

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public List<CastMember> getCast() {
        return cast;
    }

    public void setCast(List<CastMember> cast) {
        this.cast = cast;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class CastMember {
        @JsonProperty("id")
        private Long id;

        @JsonProperty("name")
        private String name;

        @JsonProperty("profile_path")
        private String profilePath;

        @JsonProperty("popularity")
        private Double popularity;

    }
}

