import {Button, Grid, Typography} from "@material-ui/core";
import React from "react";
import axios from "axios";
import {useSnackbar} from "notistack";
import {useHistory} from "react-router-dom";
import UserForgotPasswordRequest from "./dto/UserForgotPasswordRequest";
import {FormProvider, useForm} from "react-hook-form";
import {EmailTextField} from "./common/EmailTextField";

export default function ForgotPasswordForm() {

    const methods = useForm({mode: "onChange"});
    const {handleSubmit, formState: {isSubmitting, isValid}} = methods;

    const {enqueueSnackbar} = useSnackbar();

    const history = useHistory();

    const onSubmit = async (data: UserForgotPasswordRequest) => {
        return await axios.post<UserForgotPasswordRequest>("/public/forgotPassword", data)
            .then(() => {
                history.push("/login");
                enqueueSnackbar("Password reset link has been send", {variant: "info"});
            })
            .catch(() => {
                enqueueSnackbar("Sending password reset link failed", {variant: "error"});
            });
    };

    return (
        <FormProvider {...methods}>
            <form onSubmit={handleSubmit(onSubmit)}>
                <Grid container spacing={2}>
                    <Grid item xs={12}><Typography variant={"h6"}>Forgot Password</Typography></Grid>
                    <Grid item xs={12}>
                        <EmailTextField/>
                    </Grid>
                    <Grid item container xs={12} justify={"flex-end"}>
                        <Button
                            type={"submit"}
                            variant="contained"
                            color="primary"
                            disabled={isSubmitting || !isValid}
                        >Send Password Reset E-Mail</Button>
                    </Grid>
                </Grid>
            </form>
        </FormProvider>
    );
}
