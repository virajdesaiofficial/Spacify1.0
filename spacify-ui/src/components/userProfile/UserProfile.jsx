import React from 'react';
import { Link } from 'react-router-dom';
import './userProfile.css';

function UserProfile(props) {

    return (
        <div className="userProfileContainer">
            <div className="settingsPane">
                <ul>
                    <li>Profile</li>
                    <li>Password</li>
                    <li>Email</li>
                    <li><Link to="/rooms" className="rooms-link">My Rooms</Link></li>
                    <li><Link to="/incentives" className="rewards-link">Rewards</Link></li>
                    <li>Redeem</li>
                    <li>Notifications</li>
                </ul>
            </div>
            <div className="contentPane">
                <h2>User Profile</h2>
                <p>View/Make changes to your profile!</p>
            </div>
        </div>
    );
}

export default UserProfile;
