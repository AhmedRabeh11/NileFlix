package com.nileflix.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "movieId")
    @JsonBackReference
    private Movie movie;
}


