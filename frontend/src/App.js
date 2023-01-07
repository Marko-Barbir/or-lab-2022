import './App.css';

import '@fontsource/roboto/300.css';
import '@fontsource/roboto/400.css';
import '@fontsource/roboto/500.css';
import '@fontsource/roboto/700.css';

import {createBrowserRouter, RouterProvider, Route} from "react-router-dom"

import Datatable from "./routes/Datatable";
import Info from "./routes/Info";
import Profile from "./routes/Profile";

const router = createBrowserRouter([
    {
        path: "/",
        element: <Info/>
    },
    {
        path: "/data",
        element: <Datatable/>
    },
    {
        path: "/profile",
        element: <Profile/>
    }
])

function App() {
  return (
    <RouterProvider router={router}/>
  );
}

export default App;
