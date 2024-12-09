import React from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../../pages/Context/AuthContext';
import './Navbar.css';

const Navbar = ({ setSearchQuery }) => {
    const { user, logout } = useAuth();

    const handleSearchChange = (event) => {
        setSearchQuery(event.target.value);
    };

    return (
        <nav className="navbar">
            <h1 className="navbar-logo">NileFlix</h1>
            <ul className="navbar-links">
                <li><Link to="/">Home</Link></li>
                <li><Link to="/movies">Movies</Link></li>
                <li><Link to="/about">About</Link></li>
                <li><Link to="/watchlist">Watchlist</Link></li>
            </ul>
            <input
                type="text"
                placeholder="Search NileFlix"
                className="navbar-search"
                onChange={handleSearchChange}
            />
            <div className="navbar-actions">
                {user ? (
                    <>
                        <span className="navbar-icon">Hello {user.username}</span>
                        <button onClick={logout} className="navbar-icon">Logout</button>
                    </>
                ) : (
                    <Link to="/login" className="navbar-icon">Login</Link>
                )}
            </div>
        </nav>
    );
};

export default Navbar;
