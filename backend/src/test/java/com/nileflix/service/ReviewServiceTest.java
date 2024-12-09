package com.nileflix.service;

import com.nileflix.model.Review;
import com.nileflix.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetReviewsByMovieId() {
        Long movieId = 1L;
        Review review = new Review();
        review.setId(1L);
        review.setContent("Great movie!");
        review.setRating(5);
        review.setCreatedAt(new Date());

        when(reviewRepository.findByMovie_MovieId(movieId)).thenReturn(List.of(review));

        List<Review> reviews = reviewService.getReviewsByMovieId(movieId);

        assertNotNull(reviews);
        assertEquals(1, reviews.size());
        assertEquals("Great movie!", reviews.get(0).getContent());
    }

    @Test
    void testAddReview() {
        Review review = new Review();
        review.setId(1L);
        review.setContent("Great movie!");
        review.setRating(5);
        review.setCreatedAt(new Date());

        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        Review savedReview = reviewService.addReview(review);

        assertNotNull(savedReview);
        assertEquals("Great movie!", savedReview.getContent());
    }
}
