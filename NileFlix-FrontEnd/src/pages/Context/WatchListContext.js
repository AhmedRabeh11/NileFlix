import React, { createContext, useState, useContext, useEffect } from 'react';
import { fetchWatchlist, addToWatchlist, removeFromWatchlist } from '../../services/api';

const WatchlistContext = createContext();

export const useWatchlist = () => useContext(WatchlistContext);

export const WatchlistProvider = ({ children }) => {
    const [watchlist, setWatchlist] = useState([]);

    useEffect(() => {
        const loadWatchlist = async () => {
            try {
                const watchlistData = await fetchWatchlist();
                console.log('Watchlist data:', watchlistData); // Debugging line
                if (Array.isArray(watchlistData)) {
                    setWatchlist(watchlistData.map(item => ({
                        id: item.movie.movieId,
                        title: item.movie.title,
                        image: item.movie.posterImage,
                        rating: item.movie.rating
                    })));
                } else {
                    console.error('Error: Expected an array of watchlist items');
                }
            } catch (error) {
                console.error('Error loading watchlist:', error);
            }
        };

        loadWatchlist();
    }, []);

    const handleAddToWatchlist = async (movie) => {
        try {
            await addToWatchlist(movie.id);
            setWatchlist((prevWatchlist) => [...prevWatchlist, movie]);
        } catch (error) {
            console.error('Error adding to watchlist:', error);
        }
    };

    const handleRemoveFromWatchlist = async (movieId) => {
        try {
            await removeFromWatchlist(movieId);
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