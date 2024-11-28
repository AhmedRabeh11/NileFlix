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

    private String genres;

    private String posterImage;

    private String backdropImage;

    private BigDecimal rating;

    private Double popularity;

    @ManyToMany
    @JoinTable(
            name = "movie_actor",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<Actor> actors;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Trailer> trailers;
}
