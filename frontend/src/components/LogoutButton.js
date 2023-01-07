import React from "react";
import {useAuth0} from "@auth0/auth0-react";
import {Button} from "@mui/material";

const LogoutButton = ({localOnly, color, variant}) => {
    const {logout} = useAuth0();

    return (
        <Button color={color ? color : "inherit"} variant={variant ? variant : "text"}
                onClick={localOnly ?
                    () => {
                        document.cookie.split(";").forEach(function (c) {
                            document.cookie = c.replace(/^ +/, "").replace(/=.*/, "=;expires=" + new Date().toUTCString() + ";path=/");
                        });
                        window.location.href = window.location.origin;
                    } :
                    () => {
                        logout({returnTo: window.location.origin, localOnly: false});
                    }
                }>
            Log Out
        </Button>
    );
};

export default LogoutButton;