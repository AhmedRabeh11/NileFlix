package com.nileflix.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long actorId;

    private Long tmdbId;

    @Column(nullable = false)
    private String name;

    @Column(length = 5000) // Increase the length to accommodate longer biographies
    private String biography;

    private String photo;

    private Double popularity;

    @ManyToMany(mappedBy = "actors")
    @JsonBackReference
    private List<Movie> movies;

}
