package com.nileflix.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String author; // Add this field

    private String content;

    private Date createdAt;

    private Integer rating;

    @ManyToOne
    @JoinColumn(name = "movieId", referencedColumnName = "movieId")
    private Movie movie;
}
