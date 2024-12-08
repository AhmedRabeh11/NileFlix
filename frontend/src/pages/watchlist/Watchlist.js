
import React from 'react';
import { useWatchlist } from '../Context/WatchListContext';
import MovieCard from '../../component/MovieCard/MovieCard';
import './Watchlist.css';

const Watchlist = () => {
    const { watchlist } = useWatchlist();

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