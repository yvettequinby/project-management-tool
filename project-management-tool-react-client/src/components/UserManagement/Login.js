import React, { Component } from "react";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import classnames from "classnames";
import { login } from "../../actions/securityActions";
import FieldErrors from "../Layout/FieldErrors";
import ErrorMessage from "../Layout/ErrorMessage";

class Login extends Component {
  constructor() {
    super();
    this.state = {
      username: "",
      password: "",
      errors: {}
    };
    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  // update state with any new form values
  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  // life cycle hooks
  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
    if (nextProps.security.validToken) {
      this.props.history.push("/dashboard");
    }
  }

  componentDidMount() {
    if (this.props.security.validToken) {
      this.props.history.push("/dashboard");
    }
  }

  // prepare a login request object from the state and send to login() function
  onSubmit(e) {
    e.preventDefault();
    const loginRequest = {
      username: this.state.username,
      password: this.state.password
    };
    this.props.login(loginRequest, this.props.history);
  }

  render() {
    const { errors } = this.state;
    return (
      <div className="login">
        <div className="container">
          <ErrorMessage errors={errors} />
          <div className="row">
            <div className="col-md-8 m-auto">
              <h1 className="display-4 text-center">Log In</h1>
              <form onSubmit={this.onSubmit}>
                <div className="form-group">
                  <input
                    type="email"
                    placeholder="Email Address"
                    name="username"
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.username
                    })}
                    value={this.state.username}
                    onChange={this.onChange}
                  />
                  <FieldErrors msgs={errors.username} />
                </div>
                <div className="form-group">
                  <input
                    type="password"
                    placeholder="Password"
                    name="password"
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.password
                    })}
                    value={this.state.password}
                    onChange={this.onChange}
                  />
                  <FieldErrors msgs={errors.password} />
                </div>
                <input type="submit" className="btn btn-info btn-block mt-4" />
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

Login.propTypes = {
  login: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  security: state.securityContainer,
  errors: state.errors
});

export default connect(
  mapStateToProps,
  { login }
)(Login);
