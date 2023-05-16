import React from 'react';
import {Link} from 'react-router-dom';
import './userprofile.css';

function UserProfile(props) {

    return (
        <div className="userProfileContainer">
            <div className="settingsPane">
                <ul>
                    <li><Link to="/profile" className="pane-link">Profile</Link></li>
                    <li><Link to="/" className="pane-link">Password</Link></li>
                    <li><Link to="/" className="pane-link">Email</Link></li>
                    <li><Link to="/rooms" className="pane-link">My Rooms</Link></li>
                    <li><Link to="/incentives" className="pane-link">Rewards</Link></li>
                    <li><Link to="/" className="pane-link">Redeem</Link></li>
                    <li><Link to="/" className="pane-link">Notifications</Link></li>
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
