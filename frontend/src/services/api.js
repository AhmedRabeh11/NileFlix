import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api'; // Replace with your Spring Boot backend URL

export const fetchMovies = async () => {
    try {
        const response = await axios.get(`${API_BASE_URL}/movies`);
        return response.data;
    } catch (error) {
        console.error('Error fetching movies:', error);
        throw error;
    }
};

export const fetchMovieDetails = async (id) => {
    try {
        const response = await axios.get(`${API_BASE_URL}/movies/${id}`);
        return response.data;
    } catch (error) {
        console.error('Error fetching movie details:', error);
        throw error;
    }
};