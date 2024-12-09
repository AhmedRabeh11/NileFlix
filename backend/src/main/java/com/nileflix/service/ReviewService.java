package com.nileflix.service;

import com.nileflix.model.Review;
import com.nileflix.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> getReviewsByMovieId(Long movieId) {
        return reviewRepository.findByMovie_MovieId(movieId);
    }

    public Review addReview(Review review) {
        review.setCreatedAt(new Date());
        return reviewRepository.save(review);
    }
}
