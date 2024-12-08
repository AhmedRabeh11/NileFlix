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
public class TMDBGenreResponse {

    @JsonProperty("genres")
    private List<Genre> genres;

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class Genre {
        @JsonProperty("id")
        private Integer id;

        @JsonProperty("name")
        private String name;
    }
}
