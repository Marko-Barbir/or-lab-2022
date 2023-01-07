import CustomAvatar from "./CustomAvatar";
import {Grid, Typography} from "@mui/material";
import {useAuth0} from "@auth0/auth0-react";

export default function ProfileInformation() {
    const { user, isAuthenticated, isLoading } = useAuth0();

    return (
        <>
            {!isLoading && isAuthenticated &&
                <CustomAvatar fullName={user.given_name + " " + user.family_name} src={user.picture}/>}
            <Grid container textAlign="center">
                <Grid item xs={6}><Typography><b>First name: </b>{!isLoading && user.given_name}</Typography></Grid>
                <Grid item xs={6}><Typography><b>Last name: </b>{!isLoading && user.family_name}</Typography></Grid>
            </Grid>
            <Grid container textAlign="center">
                <Grid item xs={6}><Typography><b>Username: </b>{!isLoading && user.nickname}</Typography></Grid>
                <Grid item xs={6}><Typography><b>e-mail: </b>{!isLoading && user.email}</Typography></Grid>
            </Grid>
        </>
    )
}