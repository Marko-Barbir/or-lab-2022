import './App.css';

import '@fontsource/roboto/300.css';
import '@fontsource/roboto/400.css';
import '@fontsource/roboto/500.css';
import '@fontsource/roboto/700.css';

import {createBrowserRouter, RouterProvider, Route} from "react-router-dom"

import Datatable from "./routes/Datatable";
import Info from "./routes/Info";

const router = createBrowserRouter([
    {
        path: "/",
        element: <Info/>
    },
    {
        path: "/data",
        element: <Datatable/>
    }
])

function App() {
  return (
    <RouterProvider router={router}/>
  );
}

export default App;
