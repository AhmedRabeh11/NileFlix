import React, { useState, useEffect } from 'react';
import MovieCard from '../../component/MovieCard/MovieCard';
import './Home.css';

const API_KEY = '11c7aba54522527e7f5806af9ca802a7'; 
const DIRECTOR_IDS = ['1210055', '1234567', '127767']; 

const Home = ({ searchQuery }) => {
    const [movies, setMovies] = useState([]);
    const [featuredMovies, setFeaturedMovies] = useState([]);

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

                setMovies(moviesWithPosters.slice(0, 6)); 
                setFeaturedMovies(moviesWithPosters.slice(6, 18)); 
            } catch (error) {
                console.error('Error fetching movies:', error);
            }
        };

        fetchMovies();
    }, []);

    const filteredMovies = movies.filter(movie =>
        movie.title.toLowerCase().includes(searchQuery.toLowerCase())
    );

    const filteredFeaturedMovies = featuredMovies.filter(movie =>
        movie.title.toLowerCase().includes(searchQuery.toLowerCase())
    );

    return (
        <div className="home">
            <h2 className="top-title">Top on NileFlix this week</h2>
            <div className="movie-grid">
                {filteredMovies.map((movie) => (
                    <MovieCard 
                        key={movie.id} 
                        id={movie.id} 
                        title={movie.title} 
                        image={`https://image.tmdb.org/t/p/w500${movie.poster_path}`} 
                        rating={movie.vote_average} 
                    />
                ))}
            </div>
            <h2 className="featured-title">Featured Movies</h2>
            <div className="movie-grid">
                {filteredFeaturedMovies.map((movie) => (
                    <MovieCard 
                        key={movie.id} 
                        id={movie.id} 
                        title={movie.title} 
                        image={`https://image.tmdb.org/t/p/w500${movie.poster_path}`} 
                        rating={movie.vote_average} 
                    />
                ))}
            </div>
            
        </div>
    );
};

export default Home;
