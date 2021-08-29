import {Grid, Typography} from "@material-ui/core";
import React from "react";
import axios from "axios";
import {useAsync} from "react-async-hook";

async function loadHello() : Promise<string> {
    const result = await axios.get<string>("/hello");
    return result.data;
}

export default function HelloWorld() {

    const { loading, result: helloFromRestricted, error} = useAsync<string>(loadHello, []);

    if (error) {
        return (<span style={{color: "red"}}>Error: {error.message}</span>)
    } else {
        return (
            <Grid container spacing={2}>
                <Grid item xs={12}><Typography variant={"h6"}>Hello World
                    from <br/>spring-authentication-jwt-react!</Typography></Grid>
                <Grid item xs={12}>
                    Message from authorized REST endpoint: <br/>
                    {
                        !loading && <i>{helloFromRestricted}</i>
                    }
                </Grid>
            </Grid>
        )
    }
}
