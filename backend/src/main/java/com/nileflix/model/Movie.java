package com.nileflix.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;

    private Long tmdbId;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String overview;

    private Date releaseDate;

    private Integer runtime;

    private String originalLanguage;

    private String genres;

    private String posterImage;

    private String backdropImage;

    private BigDecimal rating;

    private Double popularity;

    private String productionCompanies;

    private String productionCountries;

    @ManyToMany
    @JoinTable(
            name = "movie_actor",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<Actor> actors; // Add this field to reference the actors
}
