import React from 'react';
import MovieCard from '../../component/MovieCard/MovieCard';
import './Home.css';

const Home = () => {
    const movies = [
        {
            id: 1,
            title: 'The Blue Elephant',
            poster_path: '/assets/images/the-blue-elephant.jpg',
            vote_average: 8.0,
        },
        {
            id: 2,
            title: 'The Mummy',
            poster_path: '/assets/images/el-badla.jpg',
            vote_average: 7.5,
        },
        {
            id: 3,
            title: 'Cairo Time',
            poster_path: '/path/to/cairo-time.jpg',
            vote_average: 7.0,
        },
        {
            id: 4,
            title: 'The Yacoubian Building',
            poster_path: '/assets/images/yacoubian-building.jpg',
            vote_average: 7.8,
        },
        
    ];
    return (
        <div className="home">
            <h2 className="top-title">Top on NileFlix this week</h2>
            <div className="movie-grid">
                {movies.map((movie) => (
                    <MovieCard 
                        key={movie.id} 
                        id={movie.id} 
                        title={movie.title} 
                        image={movie.poster_path} 
                        rating={movie.vote_average} 
                    />
                ))}
            </div>
        </div>
    );
};

export default Home;
