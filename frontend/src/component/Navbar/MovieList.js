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
                    key={movie.id}
                    id={movie.id}
                    title={movie.title}
                    image={`https://image.tmdb.org/t/p/w500${movie.poster_path}`}
                    rating={movie.vote_average}
                />
            ))}
        </div>
    );
};

export default MovieList;