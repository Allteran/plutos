import LoginForm from "./LoginForm";
import ErrorPage from "./util/ErrorPage";
import Root from "../route/root";
import ShiftList from "./ShiftList";
import React from "react";
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import "./App.css";


function App() {
    const router = createBrowserRouter([
        {
            path: "/",
            element: <Root />,
            errorElement: <ErrorPage />,
            children: [
                {
                    path: '/shifts',
                    element: <ShiftList />
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