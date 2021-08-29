import React from 'react';
import {BrowserRouter as Router, Redirect, Route, Switch} from "react-router-dom";
import LoginForm from "./LoginForm";
import {SnackbarProvider} from "notistack";

export default function App() {
  return (
      <SnackbarProvider anchorOrigin={ { horizontal: "center", vertical: "top" } } maxSnack={ 1 }>
          <Router>
              <Switch>
                  <Route path = "/login">
                      <LoginForm />
                  </Route>
                  <Route path = "/">
                      <Redirect to="/login" />
                  </Route>
              </Switch>
          </Router>
      </SnackbarProvider>
  );
}
