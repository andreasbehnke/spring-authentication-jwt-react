import {Button, Grid, TextField, Typography} from "@material-ui/core";
import React, {useState} from "react";
import axios from "axios";
import {useSnackbar} from "notistack";
import {useHistory} from "react-router-dom";
import UserForgotPasswordRequest from "./UserForgotPasswordRequest";

export default function ForgotPasswordForm() {

    const [submitting, setSubmitting] = useState<boolean>(false);

    const [username, setUsername] = useState<string>("", );

    const { enqueueSnackbar } = useSnackbar();

    const history = useHistory();

    const submitForgotPasswordRequest = async() => {
        if (submitting) {
            return;
        }
        setSubmitting(true);
        await axios.post<UserForgotPasswordRequest>("/public/forgotPassword", { username } )
            .then(() => {
                history.push("/login");
                enqueueSnackbar("Password reset link has been send", { variant: "info" });
            })
            .catch(() => {
                enqueueSnackbar("Sending password reset link failed", { variant: "error" });
            })
            .finally(() => setSubmitting(false));
    };

    return (
        <Grid container spacing={2}>
            <Grid item xs={12}><Typography variant={"h6"}>Forgot Password</Typography></Grid>
            <Grid item xs={12}>
                <TextField
                    fullWidth
                    label={"E-Mail"}
                    autoComplete={"email"}
                    value={username}
                    onChange={event => { setUsername(event.target.value) }}
                />
            </Grid>
            <Grid item container xs={12} justify={"flex-end"}>
                <Button
                    variant="contained"
                    color="primary"
                    onClick={submitForgotPasswordRequest}
                    disabled={submitting}
                >Send Password Reset E-Mail</Button>
            </Grid>
        </Grid>
    );
}
