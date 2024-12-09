package com.nileflix.controller;

import com.nileflix.model.Review;
import com.nileflix.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController).build();
    }

    @Test
    void testGetReviews() throws Exception {
        Long movieId = 1L;
        Review review = new Review();
        review.setId(1L);
        review.setContent("Great movie!");
        review.setRating(5);
        review.setCreatedAt(new Date());

        when(reviewService.getReviewsByMovieId(movieId)).thenReturn(List.of(review));

        mockMvc.perform(get("/api/reviews/{movieId}", movieId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value("Great movie!"));
    }

    @Test
    void testAddReview() throws Exception {
        Review review = new Review();
        review.setId(1L);
        review.setContent("Great movie!");
        review.setRating(5);
        review.setCreatedAt(new Date());

        when(reviewService.addReview(any(Review.class))).thenReturn(review);

        mockMvc.perform(post("/api/reviews")
                .contentType("application/json")
                .content("{\"content\":\"Great movie!\",\"rating\":5}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Great movie!"));
    }
}
