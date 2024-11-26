package com.nileflix.service;

import com.nileflix.model.Review;
import com.nileflix.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> getReviewsByMovieId(Long movieId) {
        return reviewRepository.findByMovieMovieId(movieId);
    }

    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }
}

