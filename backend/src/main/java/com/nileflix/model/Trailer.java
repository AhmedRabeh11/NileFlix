package com.nileflix.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Trailer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String key; // Video key for embedding
    private String site; // YouTube, etc.
    private String type; // Trailer, Teaser, etc.

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
}


