import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom'; 
import { useReviews } from '../Context/ReviewsContext'; 
import { fetchMovieDetails } from '../../services/api'; // Import the API function
import './MovieDetails.css';

const MovieDetails = () => {
    const { id } = useParams(); 
    console.log('MovieDetails id:', id); // Debugging line

    const [movie, setMovie] = useState({});
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const { reviews, addReview } = useReviews();
    const [newReview, setNewReview] = useState('');
    const [rating, setRating] = useState(0);

    useEffect(() => {
        const fetchMovieData = async () => {
            try {
                const movieData = await fetchMovieDetails(id); // Fetch movie details from backend
                console.log('Fetched movie data:', movieData); // Debugging line
                if (movieData && typeof movieData === 'object') {
                    const actorsWithImages = movieData.actors.filter(actor => actor.photo);
                    setMovie({ ...movieData, actors: actorsWithImages });
                } else {
                    console.error('Error: Expected an object for movie details');
                }
                setLoading(false);
            } catch (error) {
                setError(error.message);
                setLoading(false);
            }
        };

        fetchMovieData();
    }, [id]);

    const handleReviewSubmit = (e) => {
        e.preventDefault();
        addReview(id, { text: newReview, rating });
        setNewReview('');
        setRating(0);
    };

    const formatReleaseDate = (dateString) => {
        const options = { year: 'numeric', month: 'long', day: 'numeric' };
        return new Date(dateString).toLocaleDateString(undefined, options);
    };

    const formatDuration = (runtime) => {
        if (runtime) {
            const hours = Math.floor(runtime / 60);
            const minutes = runtime % 60;
            return `${hours}h ${minutes}m`;
        }
        return 'N/A';
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
                <img src={movie.posterImage} alt={movie.title} className="movie-poster" />
                <div className="movie-info">
                    <h1>{movie.title}</h1>
                    <p>{formatReleaseDate(movie.releaseDate)} • {formatDuration(movie.runtime)}</p>
                    <p>⭐ {movie.rating}/10</p>
                    <div className="genres">
                        {movie.genres.split(', ').map((genre, index) => (
                            <span key={index} className="genre">{genre}</span>
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
                <h2>Trailers</h2>
                {movie.trailers && movie.trailers.length > 0 ? (
                    movie.trailers.map((trailer) => (
                        <iframe
                            key={trailer.id}
                            width="560"
                            height="315"
                            src={`https://www.youtube.com/embed/${trailer.key}`}
                            title="YouTube video player"
                            frameBorder="0"
                            allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                            allowFullScreen
                        ></iframe>
                    ))
                ) : (
                    <p>No trailers available</p>
                )}
            </div>
            <div className="movie-cast">
                <h2>Cast</h2>
                <div className="cast-images">
                    {movie.actors && movie.actors.map((actor) => (
                        <div key={actor.tmdbId} className="cast-member">
                            <Link to={`/actor-details/${actor.tmdbId}`}>
                                <img src={actor.photo} alt={actor.name} className="cast-image" />
                                <p>{actor.name}</p>
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
