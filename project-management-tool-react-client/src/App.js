import React, { Component } from "react";
import "./App.css";
import "bootstrap/dist/css/bootstrap.min.css";
import Dashboard from "./components/Dashboard";
import Header from "./components/Layout/Header";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import AddProject from "./components/Project/AddProject";
import UpdateProject from "./components/Project/UpdateProject";
import { Provider } from "react-redux";
import store from "./store";
import ProjectBoard from "./components/ProjectBoard/ProjectBoard";
import AddProjectTask from "./components/ProjectBoard/AddProjectTask";
import UpdateProjectTask from "./components/ProjectBoard/UpdateProjectTask";
import Landing from "./components/Layout/Landing";
import Register from "./components/UserManagement/Register";
import Login from "./components/UserManagement/Login";
import setJwtToken from "./utils/setJwtToken";
import { logout } from "./actions/securityActions";
import setCurrentUser from "./utils/setCurrentUser";
import SecuredRoute from "./utils/SecureRoute";

const jwtToken = localStorage.jwtToken;

if (jwtToken) {
  setJwtToken(jwtToken);
  const decodedJwtToken = setCurrentUser(jwtToken);
  const currentTime = Date.now() / 1000;
  if (decodedJwtToken.exp < currentTime) {
    store.dispatch(logout());
    window.location.href = "/";
  }
}

class App extends Component {
  render() {
    return (
      <Provider store={store}>
        <Router>
          <div className="App">
            <Header />

            {
              // Public Routes
            }
            <Route exact path="/" component={Landing} />
            <Route exact path="/register" component={Register} />
            <Route exact path="/login" component={Login} />

            {
              // Private Routes
            }
            <Switch>
              <SecuredRoute exact path="/dashboard" component={Dashboard} />
              <SecuredRoute exact path="/addProject" component={AddProject} />
              <SecuredRoute
                exact
                path="/updateProject/:projectCode"
                component={UpdateProject}
              />
              <SecuredRoute
                exact
                path="/projectBoard/:projectCode"
                component={ProjectBoard}
              />
              <SecuredRoute
                exact
                path="/addProjectTask/:projectCode"
                component={AddProjectTask}
              />
              <SecuredRoute
                exact
                path="/updateProjectTask/:projectCode/:projectTaskCode"
                component={UpdateProjectTask}
              />
            </Switch>
          </div>
        </Router>
      </Provider>
    );
  }
}

export default App;
