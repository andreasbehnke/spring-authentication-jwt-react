import {Redirect, useLocation} from "react-router-dom";
import {useSnackbar} from "notistack";
import axios, {AxiosResponse} from "axios";
import UserConfirmRequest from "./dto/UserConfirmRequest";
import {useEffect} from "react";
import UserRegistrationResult from "./dto/UserRegistrationResult";
import {useAsync} from "react-async-hook";
import {CircularProgress} from "@material-ui/core";

async function confirmTicket( ticketId: string ) : Promise<UserRegistrationResult> {
    try {
        const result = await axios.post<UserConfirmRequest, AxiosResponse<UserRegistrationResult>>("/public/register/confirm", {ticketId});
        return result.data;
    } catch (error: any) {
        return error.response.data;
    }
}

export default function ConfirmRegistration() {

    const search = useLocation().search;

    const ticketId = new URLSearchParams(search).get('ticketId');

    const { loading, result: userRegistrationResult, error} = useAsync<UserRegistrationResult>(confirmTicket, [ticketId]);

    const snackbar = useSnackbar();

    useEffect(() => {
        if (!loading && !error && userRegistrationResult) {
            snackbar.enqueueSnackbar(userRegistrationResult.message, {variant: (userRegistrationResult.error) ? "error" : "success"});
        }
    });

    if (loading) {
        return (
            <CircularProgress />
        );
    } else if (userRegistrationResult) {
        return (
            <Redirect to={{ pathname: "/login", state: { initialUserName: userRegistrationResult.userName }}} />
        )
    } else {
        return (
            <Redirect to="/login" />
        )
    }
}
