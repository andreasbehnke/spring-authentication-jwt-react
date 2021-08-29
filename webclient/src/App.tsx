import React from 'react';
import {BrowserRouter as Router, Redirect, Route, Switch} from "react-router-dom";
import LoginForm from "./LoginForm";
import {SnackbarProvider} from "notistack";
import HelloWorld from "./HelloWorld";
import {Box, Grid, Paper} from "@material-ui/core";

export default function App() {
  return (
      <SnackbarProvider anchorOrigin={ { horizontal: "center", vertical: "top" } } maxSnack={ 1 }>
          <Grid container justify={"center"}>
              <Box p={2} mt={15} style={{maxWidth: "400px"}}>
                  <Paper elevation={4}>
                      <Box p={2}>
                          <Router>
                              <Switch>
                                  <Route path = "/login">
                                      <LoginForm />
                                  </Route>
                                  <Route path = "/helloWorld">
                                      <HelloWorld />
                                  </Route>
                                  <Route path = "/">
                                      <Redirect to="/login" />
                                  </Route>
                              </Switch>
                          </Router>
                      </Box>
                  </Paper>
              </Box>
          </Grid>
      </SnackbarProvider>
  );
}
