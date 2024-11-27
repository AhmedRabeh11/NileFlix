package com.nileflix.model;

import jakarta.persistence.*;

@Entity
public class Trailer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trailerId;

    private String videoKey;

    private String videoType;

    private String site;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    // Getters and Setters
}

