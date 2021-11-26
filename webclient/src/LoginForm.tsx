import {Box, Button, Grid, Link, TextField, Typography} from "@material-ui/core";
import React, {useState} from "react";
import axios from "axios";
import UserAuthenticationRequest from "./dto/UserAuthenticationRequest";
import {useSnackbar} from "notistack";
import {useHistory, useLocation} from "react-router-dom";

interface LoginRouterState {
    initialUserName : string
}

export default function LoginForm() {

    const {state: routerState} = useLocation<LoginRouterState>();

    const [submitting, setSubmitting] = useState<boolean>(false);

    const [username, setUsername] = useState<string>(routerState ? routerState.initialUserName : '', );

    const [password, setPassword] = useState<string>("", );

    const { enqueueSnackbar } = useSnackbar();

    const history = useHistory();

    const submitLogin = async() => {
        if (submitting) {
            return;
        }
        setSubmitting(true);
        await axios.post<UserAuthenticationRequest>("/public/login", { password, username } )
            .then(() => {
                history.push("/helloWorld");
            })
            .catch(() => {
                enqueueSnackbar("Login failed", { variant: "error" });
            })
            .finally(() => setSubmitting(false));
    };

    const navigateToRegistration = function () {
        history.push("/register");
    }

    return (
        <Grid container spacing={2}>
            <Grid item xs={12}><Typography variant={"h6"}>Login</Typography></Grid>
            <Grid item xs={12}>
                <TextField
                    fullWidth
                    label={"E-Mail"}
                    autoComplete={"email"}
                    value={username}
                    onChange={event => { setUsername(event.target.value) }}
                />
            </Grid>
            <Grid item xs={12}>
                <TextField
                    fullWidth
                    label={"Password"}
                    type="password"
                    autoComplete={"current-password"}
                    value={password}
                    onChange={event => { setPassword(event.target.value) }}
                />
            </Grid>
            <Grid item container xs={12} justify={"space-between"}>
                <Grid item xs={4}>
                    <Link href={"/forgotPassword"} variant={"body2"}>forgot password</Link>
                </Grid>
                <Grid item container xs={8} justify={"flex-end"}>
                    <Box mr={2}>
                        <Button
                            variant="contained"
                            onClick={navigateToRegistration}
                        >Register</Button>
                    </Box>
                    <Button
                        variant="contained"
                        color="primary"
                        onClick={submitLogin}
                        disabled={submitting}
                    >Login</Button>
                </Grid>
            </Grid>
        </Grid>
    );
}
