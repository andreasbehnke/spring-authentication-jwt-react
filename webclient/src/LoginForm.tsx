import {Box, Button, Grid, Link, Typography} from "@material-ui/core";
import React from "react";
import axios from "axios";
import UserAuthenticationRequest from "./dto/UserAuthenticationRequest";
import {useSnackbar} from "notistack";
import {useHistory, useLocation} from "react-router-dom";
import {FormProvider, useForm} from "react-hook-form";
import {EmailTextField} from "./common/EmailTextField";
import {PasswordTextField} from "./common/PasswordTextField";

interface LoginRouterState {
    initialUserName : string
}

export default function LoginForm() {

    const {state: routerState} = useLocation<LoginRouterState>();

    const methods = useForm({mode: "onChange"});
    const {handleSubmit, formState: {isSubmitting}} = methods;

    const {enqueueSnackbar} = useSnackbar();

    const history = useHistory();

    const onSubmit = (data: UserAuthenticationRequest) => {
        return axios.post<UserAuthenticationRequest>("/public/login", data)
            .then(() => {
                history.push("/helloWorld");
            })
            .catch(() => {
                enqueueSnackbar("Login failed", {variant: "error"});
            });
    };

    const navigateToRegistration = function () {
        history.push("/register");
    }

    return (
        <FormProvider {...methods}>
            <form onSubmit={handleSubmit(onSubmit)}>
                <Grid container spacing={2}>
                    <Grid item xs={12}><Typography variant={"h6"}>Login</Typography></Grid>
                    <Grid item xs={12}>
                        <EmailTextField defaultValue={routerState ? routerState.initialUserName : ""} />
                    </Grid>
                    <Grid item xs={12}>
                        <PasswordTextField />
                    </Grid>
                    <Grid item container xs={12} justify={"space-between"}>
                        <Grid item xs={4}>
                            <Link href={"/register/forgotPassword"} variant={"body2"}>forgot password</Link>
                        </Grid>
                        <Grid item container xs={8} justify={"flex-end"}>
                            <Box mr={2}>
                                <Button
                                    variant="contained"
                                    onClick={navigateToRegistration}
                                >Register</Button>
                            </Box>
                            <Button
                                type={"submit"}
                                variant="contained"
                                color="primary"
                                disabled={isSubmitting}
                            >Login</Button>
                        </Grid>
                    </Grid>
                </Grid>
            </form>
        </FormProvider>
    );
}
