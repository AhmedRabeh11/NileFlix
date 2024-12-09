import React, { useState, useEffect } from 'react';
import MovieList from '../../component/Navbar/MovieList';
import './Movies.css';

const API_KEY = '11c7aba54522527e7f5806af9ca802a7'; // Your TMDb API key
const DIRECTOR_IDS = ['1115944', '1186523', '2002988', '226425','2613122']; // Replace with actual director IDs

const Movies = ({ searchQuery }) => {
    const [movies, setMovies] = useState([]);

    useEffect(() => {
        const fetchMovies = async () => {
            try {
                const moviePromises = DIRECTOR_IDS.map((directorId) =>
                    fetch(`https://api.themoviedb.org/3/discover/movie?api_key=${API_KEY}&language=en-US&with_original_language=ar&with_crew=${directorId}&sort_by=vote_average.desc`)
                        .then((response) => {
                            if (!response.ok) {
                                throw new Error('Failed to fetch movies');
                            }
                            return response.json();
                        })
                );

                const movieResults = await Promise.all(moviePromises);
                const combinedMovies = movieResults.flatMap((result) => result.results);
                const uniqueMovies = Array.from(new Set(combinedMovies.map((movie) => movie.id)))
                    .map((id) => combinedMovies.find((movie) => movie.id === id));

                // Filter out movies without posters
                const moviesWithPosters = uniqueMovies.filter(movie => movie.poster_path);

                setMovies(moviesWithPosters); // Set the filtered movies to the state
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
