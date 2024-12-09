// src/component/MovieList.js
import React from 'react';
import './MovieList.css';
import MovieCard from '../MovieCard/MovieCard';

const MovieList = ({ movies, searchQuery }) => {
    const filteredMovies = movies.filter(movie =>
        movie.title.toLowerCase().includes(searchQuery.toLowerCase())
    );

    return (
        <div className="movie-list">
            {filteredMovies.map(movie => (
                <MovieCard
                    key={movie.movieId}
                    id={movie.movieId}
                    title={movie.title}
                    image={movie.posterImage}
                    rating={movie.rating}
                />
            ))}
        </div>
    );
};

export default MovieList;