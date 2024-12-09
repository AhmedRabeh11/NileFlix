import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api'; // Your Spring Boot backend URL

const username = 'user'; // Replace with your username
const password = '5f6eca93-d8b1-4e62-b5ee-b99735efdf55'; // Replace with the password provided by Spring Boot

const authHeader = `Basic ${btoa(`${username}:${password}`)}`;


export const registerUser = async (username, password) => {
    try {
        const response = await axios.post(`${API_BASE_URL}/auth/register`, { username, password });
        console.log('Registered user:', response.data); // Debugging line
        return response.data;
    } catch (error) {
        console.error('Error registering user:', error);
        throw error;
    }
};

export const loginUser = async (username, password) => {
    try {
        const response = await axios.post(`${API_BASE_URL}/auth/login`, { username, password });
        console.log('Logged in user:', response.data); // Debugging line
        return response.data;
    } catch (error) {
        console.error('Error logging in user:', error);
        throw error;
    }
};

export const fetchMovies = async () => {
    try {
        const response = await axios.get(`${API_BASE_URL}/movies`, {
            headers: {
                'Authorization': authHeader
            }
            
        });
        console.log('Fetched movies:', response.data); // Debugging line
        return response.data;
    } catch (error) {
        console.error('Error fetching movies:', error);
        throw error;
    }
};

export const fetchMovieDetails = async (id) => {
    try {
        const response = await axios.get(`${API_BASE_URL}/movies/${id}`, {
            headers: {
                'Authorization': authHeader
            }
        });
        console.log('Fetched movie details:', response.data); // Debugging line
        return response.data;
    } catch (error) {
        console.error('Error fetching movie details:', error);
        throw error;
    }
};

export const fetchWatchlist = async () => {
    try {
        const response = await axios.get(`${API_BASE_URL}/watchlist`);
        console.log('Fetched watchlist:', response.data); // Debugging line
        return response.data;
    } catch (error) {
        console.error('Error fetching watchlist:', error);
        throw error;
    }
};

export const addToWatchlist = async (movieId) => {
    try {
        const response = await axios.post(`${API_BASE_URL}/watchlist/${movieId}`);
        console.log('Added to watchlist:', response.data); // Debugging line
        return response.data;
    } catch (error) {
        console.error('Error adding to watchlist:', error);
        throw error;
    }
};

export const removeFromWatchlist = async (movieId) => {
    try {
        await axios.delete(`${API_BASE_URL}/watchlist/${movieId}`);
        console.log('Removed from watchlist'); // Debugging line
    } catch (error) {
        console.error('Error removing from watchlist:', error);
        throw error;
    }
};

