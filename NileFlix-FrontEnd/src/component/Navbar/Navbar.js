import React from 'react';
import { Link } from 'react-router-dom';
import './Navbar.css';

const Navbar = ({ setSearchQuery }) => {
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
                <Link to="/login" className="navbar-icon">User</Link>
                <span className="navbar-icon">🇪🇬</span>
            </div>
        </nav>
    );
};

export default Navbar;
