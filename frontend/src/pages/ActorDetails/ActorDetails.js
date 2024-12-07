// src/pages/ActorDetails.js
import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import './ActorDetails.css';

const API_KEY = '11c7aba54522527e7f5806af9ca802a7'; // Your TMDb API key

const ActorDetails = () => {
    const { id } = useParams(); // Get the actor ID from the URL
    const [actor, setActor] = useState(null);
    const [credits, setCredits] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchActorDetails = async () => {
            try {
                const actorResponse = await fetch(`https://api.themoviedb.org/3/person/${id}?api_key=${API_KEY}&language=en-US`);
                if (!actorResponse.ok) {
                    throw new Error('Failed to fetch actor details');
                }
                const actorData = await actorResponse.json();
                setActor(actorData);

                const creditsResponse = await fetch(`https://api.themoviedb.org/3/person/${id}/movie_credits?api_key=${API_KEY}&language=en-US`);
                if (!creditsResponse.ok) {
                    throw new Error('Failed to fetch actor credits');
                }
                const creditsData = await creditsResponse.json();
                setCredits(creditsData.cast);

                setLoading(false);
            } catch (error) {
                setError(error.message);
                setLoading(false);
            }
        };

        fetchActorDetails();
    }, [id]);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error: {error}</div>;
    }

    if (!actor) {
        return <div>Actor not found</div>;
    }

    const calculateAge = (birthday) => {
        const birthDate = new Date(birthday);
        const today = new Date();
        let age = today.getFullYear() - birthDate.getFullYear();
        const monthDifference = today.getMonth() - birthDate.getMonth();
        if (monthDifference < 0 || (monthDifference === 0 && today.getDate() < birthDate.getDate())) {
            age--;
        }
        return age;
    };

    return (
        <div className="actor-details">
            <div className="actor-header">
                <img src={`https://image.tmdb.org/t/p/w500${actor.profile_path}`} alt={actor.name} className="actor-photo" />
                <div className="actor-info">
                    <h1>{actor.name}</h1>
                    <p>Age: {calculateAge(actor.birthday)}</p>
                    <p>Gender: {actor.gender === 1 ? 'Female' : 'Male'}</p>
                    <p>Birthday: {actor.birthday}</p>
                    <p>Place of Birth: {actor.place_of_birth}</p>
                    <p>{actor.biography}</p>
                </div>
            </div>
            <div className="actor-credits">
                <h2>Acting Credits</h2>
                <div className="credits-list">
                    {credits.map((credit) => (
                        <div key={credit.id} className="credit-item">
                            <img src={`https://image.tmdb.org/t/p/w200${credit.poster_path}`} alt={credit.title} className="credit-poster" />
                            <div className="credit-info">
                                <h3>{credit.title}</h3>
                                <p>Character: {credit.character}</p>
                                <p>Release Date: {credit.release_date}</p>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
};

export default ActorDetails;
