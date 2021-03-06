import {Button, Grid, Typography} from "@material-ui/core";
import React from "react";
import axios from "axios";
import {useSnackbar} from "notistack";
import {useHistory} from "react-router-dom";
import UserRegistrationRequest from "./dto/UserRegistrationRequest";
import {FormProvider, useForm} from "react-hook-form";
import {ControlledTextField} from "./common/ControlledTextField";
import {PasswordTextField} from "./common/PasswordTextField";
import {EmailTextField} from "./common/EmailTextField";

export default function RegistrationForm() {

    const methods = useForm({mode: "onBlur"});
    const {handleSubmit, getValues, formState: {isSubmitting, isValid}} = methods;

    const {enqueueSnackbar} = useSnackbar();

    const history = useHistory();

    const onSubmit = (data: UserRegistrationRequest) => {
        return axios.post<UserRegistrationRequest>("/public/register", data)
            .then(() => {
                history.push("/login");
                enqueueSnackbar("Confirmation email has been send", {variant: "success"});
            })
            .catch(({response}) => {
                enqueueSnackbar(response.data.message, {variant: "error"});
            })
    };

    return (
        <FormProvider {...methods}>
            <form onSubmit={handleSubmit(onSubmit)}>
                <Grid container spacing={2}>
                    <Grid item xs={12}><Typography variant={"h6"}>Registration</Typography></Grid>
                    <Grid item xs={12}>
                        <EmailTextField/>
                    </Grid>
                    <Grid item xs={12}>
                        <PasswordTextField/>
                    </Grid>
                    <Grid item xs={12}>
                        <ControlledTextField
                            name={"password2"}
                            fullWidth
                            label={"repeat Password"}
                            type="password"
                            defaultValue={""}
                            rules={{
                                validate: value => {
                                    return (getValues("password") !== value) ? "passwords do not match" : true
                                },
                                required: true
                            }}
                        />
                    </Grid>
                    <Grid item container xs={12} justify={"flex-end"}>
                        <Button
                            variant="contained"
                            color="primary"
                            type={"submit"}
                            disabled={isSubmitting || !isValid}
                        >Register</Button>
                    </Grid>
                </Grid>
            </form>
        </FormProvider>
    );
}
