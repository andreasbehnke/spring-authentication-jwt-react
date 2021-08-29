import {Box, Button, Grid, Paper, TextField, Typography} from "@material-ui/core";
import React from "react";

export default function LoginForm() {
    return (
        <Grid container justify={"center"}>
            <Box p={2} mt={15} style={{maxWidth: "400px"}}>
                <Paper elevation={4}>
                    <Box p={2}>
                        <Grid container spacing={2}>
                            <Grid item xs={12}><Typography variant={"h6"}>Login</Typography></Grid>
                            <Grid item xs={12}><TextField fullWidth label={"E-Mail"} autoComplete={"email"}/></Grid>
                            <Grid item xs={12}><TextField fullWidth label={"Password"} type="password" autoComplete={"current-password"} /></Grid>
                            <Grid item container xs={12} justify={"flex-end"}>
                                <Button variant="contained" color="primary">Login</Button>
                            </Grid>
                        </Grid>
                    </Box>
                </Paper>
            </Box>
        </Grid>
    );
}
