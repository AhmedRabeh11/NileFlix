package com.nileflix.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


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

}
