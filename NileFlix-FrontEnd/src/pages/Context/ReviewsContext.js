// src/context/ReviewsContext.js
import React, { createContext, useContext, useState } from 'react';
import { useAuth } from './AuthContext';

const ReviewsContext = createContext();

export const useReviews = () => useContext(ReviewsContext);

export const ReviewsProvider = ({ children }) => {
    const [reviews, setReviews] = useState({});
    const { user } = useAuth();

    const addReview = (movieId, review) => {
        const reviewWithAuthor = { ...review, author: user.username };
        setReviews((prevReviews) => ({
            ...prevReviews,
            [movieId]: [...(prevReviews[movieId] || []), reviewWithAuthor],
        }));
    };

    return (
        <ReviewsContext.Provider value={{ reviews, addReview }}>
            {children}
        </ReviewsContext.Provider>
    );
};