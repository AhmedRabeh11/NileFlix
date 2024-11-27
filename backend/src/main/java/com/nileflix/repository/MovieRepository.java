package com.nileflix.repository;

import com.nileflix.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findTop10ByOrderByRatingDesc();
    List<Movie> findByGenresContaining(String genre);
}

