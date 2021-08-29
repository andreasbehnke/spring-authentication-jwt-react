import React from 'react';
import {BrowserRouter as Router, Redirect, Route, Switch} from "react-router-dom";
import LoginForm from "./LoginForm";

export default function App() {
  return (
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
  );
}
