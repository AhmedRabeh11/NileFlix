package com.nileflix.model;


import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long actorId;

    @Column(nullable = false)
    private String name;

    private Date birthDate;
    private String biography;
    private String photo;

    @ManyToMany(mappedBy = "actors")
    private List<Movie> movies;

}

