import {useState} from "react";
import {Button} from "@mui/material";
import {useAuth0} from "@auth0/auth0-react";

export default function RefreshDatasetButton() {
    const { login, getAccessTokenWithPopup, getAccessTokenSilently } = useAuth0();

    const opts = {
        audience: 'http://zari.fer.hr/or/lab/mb52211/api/v2/',
        scope: 'write:update_dataset',
        method: 'POST'
    };

    const { audience, scope, ...fetchOptions } = opts;

    const [state, setState] = useState({
        disabled : false,
        text : "Update dataset"
    })

    const handleClick = async (e) => {
        if(!state.disabled) {
            try {
                const accessToken = await getAccessTokenSilently({audience, scope});
                const url = 'http://localhost:8080/api/v2/refresh_local';
                await fetch(url, {
                    ...fetchOptions,
                    headers: {
                        ...fetchOptions.headers,
                        // Add the Authorization header to the existing headers
                        Authorization: `Bearer ${accessToken}`,
                    }
                })

                setState({disabled: true, text: "Update sent"})
            }
            catch (e) {
                getAccessTokenWithPopup(opts);
            }
        }
    }

    return (
        <Button onClick={handleClick} variant="contained" disabled={state.disabled}>{state.text}</Button>
    )
}