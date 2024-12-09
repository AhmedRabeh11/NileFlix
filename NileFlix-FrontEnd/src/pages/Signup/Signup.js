import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { registerUser } from '../../services/api';
import './Signup.css';

const Signup = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await registerUser(username, password);
            navigate('/login');
        } catch (error) {
            console.error('Signup failed:', error);
        }
    };

    return (
        <div className="signup-container">
            <div className="signup-card">
                <h2>Sign Up</h2>
                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label htmlFor="username">Username</label>
                        <input
                            type="text"
                            id="username"
                            name="username"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="password">Password</label>
                        <input
                            type="password"
                            id="password"
                            name="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>
                    <button type="submit" className="signup-button">Sign Up</button>
                </form>
                <div className="signup-footer">
                    <p>Already have an account? <a href="/login">Login</a></p>
                </div>
            </div>
        </div>
    );
};

export default Signup;
