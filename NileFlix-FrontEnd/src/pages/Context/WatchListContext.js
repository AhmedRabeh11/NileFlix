import React, { createContext, useState, useContext, useEffect } from 'react';
import { fetchWatchlist, addToWatchlist, removeFromWatchlist } from '../../services/api';

const WatchlistContext = createContext();

export const useWatchlist = () => useContext(WatchlistContext);

export const WatchlistProvider = ({ children }) => {
    const [watchlist, setWatchlist] = useState([]);
    const userId = 'user123'; // Replace with actual user ID

    useEffect(() => {
        const loadWatchlist = async () => {
            try {
                const watchlistData = await fetchWatchlist(userId);
                setWatchlist(watchlistData.map(item => ({
                    id: item.movie.movieId,
                    title: item.movie.title,
                    image: item.movie.posterImage,
                    rating: item.movie.rating
                })));
            } catch (error) {
                console.error('Error loading watchlist:', error);
            }
        };

        loadWatchlist();
    }, [userId]);

    const handleAddToWatchlist = async (movie) => {
        try {
            await addToWatchlist(userId, movie.id);
            setWatchlist((prevWatchlist) => [...prevWatchlist, movie]);
        } catch (error) {
            console.error('Error adding to watchlist:', error);
        }
    };

    const handleRemoveFromWatchlist = async (movieId) => {
        try {
            await removeFromWatchlist(userId, movieId);
            setWatchlist((prevWatchlist) => prevWatchlist.filter(movie => movie.id !== movieId));
        } catch (error) {
            console.error('Error removing from watchlist:', error);
        }
    };

    return (
        <WatchlistContext.Provider value={{ watchlist, addToWatchlist: handleAddToWatchlist, removeFromWatchlist: handleRemoveFromWatchlist }}>
            {children}
        </WatchlistContext.Provider>
    );
};