import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom'; 
import { useReviews } from '../Context/ReviewsContext'; 
import './MovieDetails.css';

const API_KEY = '11c7aba54522527e7f5806af9ca802a7'; 

const MovieDetails = () => {
    const { id } = useParams(); 
    const [movie, setMovie] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const { reviews, addReview } = useReviews();
    const [newReview, setNewReview] = useState('');
    const [rating, setRating] = useState(0);

    useEffect(() => {
        const fetchMovieDetails = async () => {
            try {
                const response = await fetch(`https://api.themoviedb.org/3/movie/${id}?api_key=${API_KEY}&language=en-US&append_to_response=videos,credits`);
                if (!response.ok) {
                    throw new Error('Failed to fetch movie details');
                }
                const data = await response.json();
                setMovie(data);
                setLoading(false);
            } catch (error) {
                setError(error.message);
                setLoading(false);
            }
        };

        fetchMovieDetails();
    }, [id]);

    const handleReviewSubmit = (e) => {
        e.preventDefault();
        addReview(id, { text: newReview, rating });
        setNewReview('');
        setRating(0);
    };

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error: {error}</div>;
    }

    if (!movie) {
        return <div>Movie not found</div>;
    }

    return (
        <div className="movie-details">
            <div className="movie-header">
                <img src={`https://image.tmdb.org/t/p/w500${movie.poster_path}`} alt={movie.title} className="movie-poster" />
                <div className="movie-info">
                    <h1>{movie.title}</h1>
                    <p>{movie.release_date} • {movie.runtime} min</p>
                    <p>⭐ {movie.vote_average}/10</p>
                    <div className="genres">
                        {movie.genres.map((genre) => (
                            <span key={genre.id} className="genre">{genre.name}</span>
                        ))}
                    </div>
                    <p>{movie.overview}</p>
                </div>
            </div>
            <div className="movie-overview">
                <h2>Overview</h2>
                <p>{movie.overview}</p>
            </div>
            <div className="movie-trailer">
                {movie.videos.results.length > 0 && (
                    <iframe
                        width="560"
                        height="315"
                        src={`https://www.youtube.com/embed/${movie.videos.results[0].key}`}
                        title="YouTube video player"
                        frameBorder="0"
                        allow="accelerometer; autoplay; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                        allowFullScreen
                    ></iframe>
                )}
            </div>
            <div className="movie-cast">
                <h2>Cast</h2>
                <div className="cast-images">
                    {movie.credits.cast.slice(0, 5).map((cast) => (
                        <div key={cast.id} className="cast-member">
                            <Link to={`/actor-details/${cast.id}`}>
                                <img src={`https://image.tmdb.org/t/p/w200${cast.profile_path}`} alt={cast.name} className="cast-image" />
                                <p>{cast.name}</p>
                            </Link>
                        </div>
                    ))}
                </div>
            </div>
            <div className="movie-reviews">
                <h2>Reviews</h2>
                <form onSubmit={handleReviewSubmit}>
                    <div className="form-group">
                        <label htmlFor="review">Leave a Review:</label>
                        <textarea
                            id="review"
                            value={newReview}
                            onChange={(e) => setNewReview(e.target.value)}
                            required
                        ></textarea>
                    </div>
                    <div className="form-group">
                        <label htmlFor="rating">Rating:</label>
                        <input
                            type="number"
                            id="rating"
                            value={rating}
                            onChange={(e) => setRating(Number(e.target.value))}
                            min="0"
                            max="10"
                            required
                        />
                    </div>
                    <button type="submit">Submit Review</button>
                </form>
                <div className="reviews-list">
                    {reviews[id]?.map((review, index) => (
                        <div key={index} className="review">
                            <p>{review.text}</p>
                            <p>Rating: {review.rating}/10</p>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
};

export default MovieDetails;
