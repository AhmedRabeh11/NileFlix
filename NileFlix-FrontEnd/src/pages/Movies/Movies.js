import React, { useState, useEffect } from 'react';
import MovieList from '../../component/Navbar/MovieList';
import { fetchMovies } from '../../services/api'; // Import the API function
import './Movies.css';

const Movies = ({ searchQuery }) => {
    const [movies, setMovies] = useState([]);

    useEffect(() => {
        const fetchMoviesData = async () => {
            try {
                const moviesData = await fetchMovies(); // Fetch movies from backend
                console.log('Movies data:', moviesData); // Debugging line
                if (Array.isArray(moviesData)) {
                    const moviesWithPosters = moviesData.filter(movie => movie.posterImage);
                    setMovies(moviesWithPosters);
                } else {
                    console.error('Error: Expected an array of movies');
                }
            } catch (error) {
                console.error('Error fetching movies:', error);
            }
        };

        fetchMoviesData();
    }, []);

    return (
        <div className="movies-container">
            <header className="movies-header">
                <h1>Egyptian Movies</h1>
                <span className="subtitle">Discover the best of Egyptian cinema</span>
            </header>
            <MovieList movies={movies} searchQuery={searchQuery} />
        </div>
    );
};

export default Movies;
