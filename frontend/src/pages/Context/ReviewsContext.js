// src/context/ReviewsContext.js
import React, { createContext, useContext, useState } from 'react';

const ReviewsContext = createContext();

export const useReviews = () => useContext(ReviewsContext);

export const ReviewsProvider = ({ children }) => {
    const [reviews, setReviews] = useState({});

    const addReview = (movieId, review) => {
        setReviews((prevReviews) => ({
            ...prevReviews,
            [movieId]: [...(prevReviews[movieId] || []), review],
        }));
    };

    return (
        <ReviewsContext.Provider value={{ reviews, addReview }}>
            {children}
        </ReviewsContext.Provider>
    );
};