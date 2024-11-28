package com.nileflix.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
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

    private String biography;

    private String photo;

    private Double popularity;

    @ManyToMany(mappedBy = "actors")
    private List<Movie> movies;

}


