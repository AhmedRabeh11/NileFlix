import React, { useState, useEffect } from 'react';
import MovieList from '../../component/Navbar/MovieList';
import './Movies.css'; // Import the CSS file

const API_KEY = '11c7aba54522527e7f5806af9ca802a7'; // Your TMDb API key

const Movies = ({ searchQuery, setSearchQuery }) => {
    const [movies, setMovies] = useState([]);

    useEffect(() => {
        const fetchMovies = async () => {
            try {
                const response = await fetch(`https://api.themoviedb.org/3/discover/movie?api_key=${API_KEY}&language=en-US&with_original_language=ar`);
                if (!response.ok) {
                    throw new Error('Failed to fetch movies');
                }
                const data = await response.json();
                setMovies(data.results);
            } catch (error) {
                console.error('Error fetching movies:', error);
            }
        };

        fetchMovies();
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
