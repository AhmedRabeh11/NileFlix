package com.nileflix.repository;

import com.nileflix.model.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {
    void deleteByMovie_MovieId(Long movieId);
}
