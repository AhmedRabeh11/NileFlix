import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './component/Navbar/Navbar';
import Home from './pages/Home/Home';
import Movies from './pages/Movies/Movies';
import About from './pages/About/About';
import MovieDetails from './pages/MovieDetails/MovieDetails';
import Login from './pages/Login/Login'; 

const App = () => {
    const [searchQuery, setSearchQuery] = useState('');

    return (
        <Router>
            <Navbar setSearchQuery={setSearchQuery} />
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/movies" element={<Movies searchQuery={searchQuery} />} />
                <Route path="/about" element={<About />} />
                <Route path="/movie-details/:id" element={<MovieDetails />} />
                <Route path="/login" element={<Login />} />
            </Routes>
        </Router>
    );
};

export default App;
