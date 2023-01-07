import {Avatar, Button, Container, Grid, Stack, Typography} from "@mui/material";
import AuthBar from "../components/AuthBar";
import {withAuthenticationRequired} from "@auth0/auth0-react";
import RefreshDatasetButton from "../components/RefreshDatasetButton";
import LogoutButton from "../components/LogoutButton";
import ProfileInformation from "../components/ProfileInformation";

function Profile() {
    return(
        <Container>
            <AuthBar/>
            <Grid container
            direction="column"
            alignItems="center"
            justifyContent="center"
            sx={{marginTop:"10px"}}>
                <ProfileInformation/>
                <Stack direction="row" spacing={5} marginTop="20px">
                    <RefreshDatasetButton/>
                    <LogoutButton variant="contained" color="error" localOnly={true}/>
                </Stack>
            </Grid>
        </Container>
    )
}

export default withAuthenticationRequired(Profile);