import React, { useState, useEffect } from 'react';
import MovieCard from '../../component/MovieCard/MovieCard';
import { fetchMovies } from '../../services/api'; // Import the API function
import './Home.css';

const Home = ({ searchQuery }) => {
    const [movies, setMovies] = useState([]);
    const [featuredMovies, setFeaturedMovies] = useState([]);

    useEffect(() => {
        const fetchMoviesData = async () => {
            try {
                const moviesData = await fetchMovies(); // Fetch movies from backend
                console.log('Movies data:', moviesData); // Debugging line
                if (Array.isArray(moviesData)) {
                    const moviesWithPosters = moviesData.filter(movie => movie.posterImage);
                    setMovies(moviesWithPosters.slice(0, 6));
                    setFeaturedMovies(moviesWithPosters.slice(6, 18));
                } else {
                    console.error('Error: Expected an array of movies');
                }
            } catch (error) {
                console.error('Error fetching movies:', error);
            }
        };

        fetchMoviesData();
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
                        key={movie.movieId} 
                        id={movie.movieId} 
                        title={movie.title} 
                        image={movie.posterImage} 
                        rating={movie.rating} 
                    />
                ))}
            </div>
            <h2 className="featured-title">Featured Movies</h2>
            <div className="movie-grid">
                {filteredFeaturedMovies.map((movie) => (
                    <MovieCard 
                        key={movie.movieId} 
                        id={movie.movieId} 
                        title={movie.title} 
                        image={movie.posterImage} 
                        rating={movie.rating} 
                    />
                ))}
            </div>
        </div>
    );
};

export default Home;
