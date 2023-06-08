import React, {useEffect, useState} from 'react';
import './userprofile.css';
import Profile from "./profile/Profile";
import Rooms from "./rooms/Rooms";
import Incentives from "./incentives/Incentives";
import {GET_USER_PROFILE_API, USER_NAME_KEY} from "../../endpoints";
import LoadingSpinner from "../utilities/LoadingSpinner";
import Password from "./password/Password";
import SignInDisclaimer from "../utilities/SignInDisclaimer";

function UserProfile(props) {
    const initialState = {
        selectedTab: 1,
        loading: false,
        user: undefined,
    }
    const [state, setState] = useState(initialState);

    const fetchUserData = () => {
        const userName = global.sessionStorage.getItem(USER_NAME_KEY);
        if (!userName) {
            return;
        }
        const url = GET_USER_PROFILE_API + userName;
        setState({...state, loading: true});
        fetch(url)
            .then((res) => res.json())
            .then((data) => {
                setState({...state, user: data, loading: false});
            });
    };

    useEffect(() => {
        fetchUserData();
    }, []);

    const displayRightPane = () => {
        switch(state.selectedTab) {
            case 1: {
                if (state.user) {
                    return (
                        <Profile
                            user={state.user.user}
                            macAddressList={state.user.macAddresses}
                        />
                    );
                } else {
                    return (<div />);
                }
            }
            case 2: {
                return (
                    <Rooms
                        ownedRooms={state.user.ownedRooms}
                        subscribedRooms={state.user.subscribedRooms}
                    />
                );
            }
            case 3: {
                return (
                    <Incentives
                        incentives={state.user.incentives}
                        totalIncentive={state.user.user.totalIncentives}
                    />
                );
            }
            case 4: {
                return (
                    <Password
                        userId={state.user.user.userId}
                    />
                );
            }
            default: {
                return (
                    <div>
                        <h2 style={{textAlign: 'center'}}>Coming soon!</h2>
                    </div>
                );
            }
        }
    };

    const display = () => {
        if (global.sessionStorage.getItem(USER_NAME_KEY)) {
            return (
                <div className="userProfileContainer">
                    <LoadingSpinner show={state.loading} />
                    <div className="settingsPane">
                        <ul>
                            <li onClick={e => setState({...state, selectedTab: 1})} className={state.selectedTab === 1 ? "active" : "pane-link"} >Profile</li>
                            <li onClick={e => setState({...state, selectedTab: 4})} className={state.selectedTab === 4 ? "active" : "pane-link"} >Password</li>
                            <li onClick={e => setState({...state, selectedTab: 2})} className={state.selectedTab === 2 ? "active" : "pane-link"} >Subscribed Rooms</li>
                            <li onClick={e => setState({...state, selectedTab: 3})} className={state.selectedTab === 3 ? "active" : "pane-link"} >My Rewards</li>
                            <li onClick={e => setState({...state, selectedTab: 5})} className={state.selectedTab === 5 ? "active" : "pane-link"} >Redeem</li>
                        </ul>
                    </div>
                    <div className="contentPane">
                        {displayRightPane()}
                    </div>
                </div>
            );
        } else {
            return (
                <div style={{textAlign: 'center'}}>
                    <SignInDisclaimer
                        header = "Please Sign In to view your profile!"
                    />
                </div>
            );
        }
    };

    return display();
}

export default UserProfile;
