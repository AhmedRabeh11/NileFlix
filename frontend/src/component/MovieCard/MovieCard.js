import React from 'react';
import { Link } from 'react-router-dom';
import { useWatchlist } from '../../pages/Context/WatchListContext';
import './MovieCard.css';

const MovieCard = ({ id, title, image, rating }) => {
    const { watchlist, addToWatchlist, removeFromWatchlist } = useWatchlist();
    const isInWatchlist = watchlist.some(movie => movie.id === id);

    const handleWatchlistClick = () => {
        if (isInWatchlist) {
            removeFromWatchlist(id);
        } else {
            addToWatchlist({ id, title, image, rating });
        }
    };

    return (
        <div className="movie-card">
            <img src={image} alt={title} className="movie-card-image" />
            <div className="movie-card-content">
                <h3 className="movie-card-title">{title}</h3>
                <div className="movie-card-rating">
                    <span>⭐ {rating}/10</span>
                </div>
                <Link to={`/movie-details/${id}`}>
                    <button className="trailer-button">▶ Trailer</button>
                </Link>
                <button className="watchlist-button" onClick={handleWatchlistClick}>
                    {isInWatchlist ? 'Remove from Watchlist' : 'Add to Watchlist'}
                </button>
            </div>
        </div>
    );
};

export default MovieCard;
