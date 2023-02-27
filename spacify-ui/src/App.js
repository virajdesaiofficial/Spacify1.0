import './App.css';
import Navigation from "./components/Nav/Navigation";
import Home from "./components/Home/Home";
import Create from "./components/CreateRoom/Create";
import Team from "./components/team/Team";
import React from "react";
import {createBrowserRouter, RouterProvider} from "react-router-dom";

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
