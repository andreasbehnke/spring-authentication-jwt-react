import {Box, Button, Grid, Paper, TextField, Typography} from "@material-ui/core";
import React, {useState} from "react";
import axios from "axios";
import UserAuthenticationRequest from "./UserAuthenticationRequest";
import {useSnackbar} from "notistack";

export default function LoginForm() {

    const [submitting, setSubmitting] = useState<boolean>(false);

    const [username, setUsername] = useState<string>("", );

    const [password, setPassword] = useState<string>("", );

    const { enqueueSnackbar } = useSnackbar();

    const submitLogin = async() => {
        if (submitting) {
            return;
        }
        setSubmitting(true);
        await axios.post<UserAuthenticationRequest>("/public/login", { password, username } )
            .catch(() => {
                enqueueSnackbar("Login failed", { variant: "error" });
            })
            .finally(() => setSubmitting(false));
    };

    return (
        <Grid container justify={"center"}>
            <Box p={2} mt={15} style={{maxWidth: "400px"}}>
                <Paper elevation={4}>
                    <Box p={2}>
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
                            <Grid item container xs={12} justify={"flex-end"}>
                                <Button
                                    variant="contained"
                                    color="primary"
                                    onClick={submitLogin}
                                    disabled={submitting}
                                >Login</Button>
                            </Grid>
                        </Grid>
                    </Box>
                </Paper>
            </Box>
        </Grid>
    );
}
