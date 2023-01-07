import React from "react";
import {useAuth0} from "@auth0/auth0-react";
import LogoutButton from "./LogoutButton";
import LoginButton from "./LoginButton";
import {AppBar, Box, Button, IconButton, Link, Toolbar, Typography} from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu"
import CustomAvatar from "./CustomAvatar";

const AuthBar = () => {
    const {user, isAuthenticated, isLoading} = useAuth0();

    return (
        <Box sx={{flexGrow: 1}}>
            <AppBar position="static">
                <Toolbar>
                    <Link href={window.location.origin} underline="none" color="inherit" variant="h6"
                          sx={{marginRight: "50px"}}>
                        Info
                    </Link>
                    <Link href={window.location.origin + "/data"} underline="none" color="inherit" variant="h6"
                          sx={{flexGrow: 1}}>
                        Data Table
                    </Link>
                    {!isLoading && isAuthenticated && <Link href={window.location.origin + "/profile"} underline="none" color="inherit" variant="h6"
                           sx={{marginRight: "50px"}}>
                        Profile
                    </Link>}
                    {isLoading ? null : isAuthenticated ? <LogoutButton/>
                        : <LoginButton/>}
                </Toolbar>
            </AppBar>
        </Box>
    )
}

export default AuthBar;