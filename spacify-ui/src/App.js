import './App.css';
import Navigation from "./components/nav/Navigation";
import Home from "./components/home/Home";
import Reservation from "./components/reservation/Reservation";
import Team from "./components/team/Team";
import UserProfile from "./components/userProfile/UserProfile";
import Incentives from "./components/incentives/Incentives";
import Rooms from "./components/rooms/Rooms";
import Rules from "./components/rules/Rules";
import React from "react";
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import { BrowserRouter as Router, Route } from 'react-router-dom';

const router = createBrowserRouter([
    {
        element: <Navigation />,
        children: [
            {
                path: "/",
                element: <Home />
            },
            {
                path: "/reservation",
                element: <Reservation />
            },
            {
                path: "/team",
                element: <Team />
            },
            {
                path: "/user",
                element: <UserProfile />
            },
            {
                path: "/incentives",
                element: <Incentives />
            },
            {
                path: "/rooms",
                element: <Rooms />
            },
            {
                path: "/rules",
                element: <Rules />
            }

        ]
    }
    ]);

function App() {
  return (
      <div>
          <RouterProvider router={router}/>

      </div>
  );
}

export default App;
