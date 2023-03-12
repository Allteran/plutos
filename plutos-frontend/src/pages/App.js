import LoginForm from "./LoginForm";
import ErrorPage from "./ErrorPage";
import Root from "../route/root";
import ShiftList from "./shifts/ShiftList";
import React from "react";
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import "./App.css";
import ShiftRegistration from "./shifts/ShiftRegistration";
import Profile from "./profile/Profile";


function App() {


    const router = createBrowserRouter([
        {
            path: "",
            element: <Root />,
            errorElement: <ErrorPage />,
            children: [
                {
                    path: '/shifts',
                    element: <ShiftList />
                },
                {
                    path:'/shifts/new',
                    element: <ShiftRegistration />
                },
                {
                    path: '/profile',
                    element: <Profile/>
                }
            ]
        },
        {
            path: "/login",
            element: <LoginForm />,
            errorElement: <ErrorPage />
        }
    ]);



    return (
        <React.StrictMode>
            <RouterProvider router={router}/>
        </React.StrictMode>
    )
}

export default App;