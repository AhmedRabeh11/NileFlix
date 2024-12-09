// src/pages/Watchlist.js
import React from 'react';
import { useNavigate } from 'react-router-dom';
import { useWatchlist } from '../Context/WatchListContext';
import { useAuth } from '../Context/AuthContext';
import MovieCard from '../../component/MovieCard/MovieCard';
import './Watchlist.css';

const Watchlist = () => {
    const { watchlist } = useWatchlist();
    const { user } = useAuth();
    const navigate = useNavigate();

    if (!user) {
        navigate('/login');
        return null;
    }

    return (
        <div className="watchlist">
            <h2 className="watchlist-title">My Watchlist</h2>
            <div className="movie-grid">
                {watchlist.map((movie) => (
                    <MovieCard 
                        key={movie.id} 
                        id={movie.id} 
                        title={movie.title} 
                        image={movie.image} 
                        rating={movie.rating} 
                    />
                ))}
            </div>
        </div>
    );
};

export default Watchlist;