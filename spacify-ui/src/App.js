import './App.css';
import Navigation from "./components/nav/Navigation";
import Home from "./components/home/Home";
import Create from "./components/createRoom/Create";
import Team from "./components/team/Team";
import React from "react";
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import Rules from "./components/rules/Rules";
import SignIn from "./components/signin/SignIn";
import SignUp from "./components/signup/SignUp";
import UserProfile from "./components/userProfile/UserProfile";
import Reservation from "./components/reservation/Reservation";
import Subscribe from "./components/subscribe/Subscribe";
import {USER_NAME_KEY} from "./endpoints";

const router = createBrowserRouter([
    {
        element: <Navigation />,
        children: [
            {
                path: "/",
                element: <Home />
            },
            {
                path: "/create",
                element: <Create />
            },
            {
                path: "/team",
                element: <Team />
            },
            {
                path: "/reserve",
                element: <Reservation />
            },
            {
                path: "/rules",
                element: <Rules />
            },
            {
                path: "/signIn",
                element: <SignIn />
            },
            {
                path: "/signUp",
                element: <SignUp />
            },
            {
                path: "/user",
                element: <UserProfile />
            },
            {
                path: "/subscribe",
                element: <Subscribe />
            }

        ]
    }
    ]);

function App() {
    // check whether session logged on or remember me was present, if remember me then we copy log info into session
    let loggedUserName = global.sessionStorage.getItem(USER_NAME_KEY);
    if (!loggedUserName && global.localStorage) {
        loggedUserName = global.localStorage.getItem(USER_NAME_KEY);
        if (loggedUserName)
            global.sessionStorage.setItem(USER_NAME_KEY, loggedUserName);
    }

    return (
        <section className="app_container">
            <RouterProvider router={router}/>
        </section>
    );
}

export default App;
