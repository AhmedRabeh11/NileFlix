package com.nileflix.repository;

import com.nileflix.model.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {
    List<Watchlist> findByUserId(String userId);
    void deleteByUserIdAndMovie_MovieId(String userId, Long movieId);
}
