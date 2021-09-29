import {Button, Grid, TextField, Typography} from "@material-ui/core";
import React, {useState} from "react";
import axios from "axios";
import {useSnackbar} from "notistack";
import {useHistory} from "react-router-dom";
import UserRegistrationRequest from "./UserRegistrationRequest";

export default function RegistrationForm() {

    const [submitting, setSubmitting] = useState<boolean>(false);

    const [username, setUsername] = useState<string>("", );

    const [password, setPassword] = useState<string>("", );

    const [password2, setPassword2] = useState<string>("", );

    const { enqueueSnackbar } = useSnackbar();

    const history = useHistory();

    const submitRegistration = async() => {
        if (submitting) {
            return;
        }
        setSubmitting(true);
        await axios.post<UserRegistrationRequest>("/public/register", { password, password2, username } )
            .then(() => {
                history.push("/login");
                enqueueSnackbar("Confirmation email has been send", { variant: "success" });
            })
            .catch(({response}) => {
                enqueueSnackbar( response.data.message, { variant: "error" });
            })
            .finally(() => setSubmitting(false));
    };

    return (
        <Grid container spacing={2}>
            <Grid item xs={12}><Typography variant={"h6"}>Registration</Typography></Grid>
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
                    value={password}
                    onChange={event => { setPassword(event.target.value) }}
                />
            </Grid>
            <Grid item xs={12}>
                <TextField
                    fullWidth
                    label={"repeat Password"}
                    type="password"
                    value={password2}
                    onChange={event => { setPassword2(event.target.value) }}
                />
            </Grid>
            <Grid item container xs={12} justify={"flex-end"}>
                <Button
                    variant="contained"
                    color="primary"
                    onClick={submitRegistration}
                    disabled={submitting}
                >Register</Button>
            </Grid>
        </Grid>
    );
}
