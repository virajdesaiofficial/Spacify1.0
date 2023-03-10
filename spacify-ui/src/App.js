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
            }
        ]
    }
    ]);

function App() {
  return (
      <section className="app_container">
          <RouterProvider router={router}/>
      </section>
  );
}

export default App;
