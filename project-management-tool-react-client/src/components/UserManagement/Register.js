import React, { Component } from "react";
import { createNewUser } from "../../actions/securityActions";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import classnames from "classnames";
import FieldErrors from "../Layout/FieldErrors";
import ErrorMessage from "../Layout/ErrorMessage";

class Register extends Component {
  constructor() {
    super();

    this.state = {
      username: "",
      fullName: "",
      password: "",
      confirmPassword: "",
      errors: {}
    };
    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  // life cycle hooks
  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
  }

  componentDidMount() {
    if (this.props.security.validToken) {
      this.props.history.push("/dashboard");
    }
  }

  onSubmit(e) {
    e.preventDefault();
    const newUser = {
      username: this.state.username,
      fullName: this.state.fullName,
      password: this.state.password,
      confirmPassword: this.state.confirmPassword
    };

    this.props.createNewUser(newUser, this.props.history);
  }

  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  render() {
    const { errors } = this.state;
    return (
      <div className="register">
        <div className="container">
          <ErrorMessage errors={errors} />
          <div className="row">
            <div className="col-md-8 m-auto">
              <h1 className="display-4 text-center">Sign Up</h1>
              <p className="lead text-center">Create your Account</p>
              <form onSubmit={this.onSubmit}>
                <div className="form-group">
                  <input
                    type="text"
                    placeholder="Name"
                    name="fullName"
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.fullName
                    })}
                    value={this.state.fullName}
                    onChange={this.onChange}
                  />
                  <FieldErrors msgs={errors.fullName} />
                </div>
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
                <div className="form-group">
                  <input
                    type="password"
                    placeholder="Confirm Password"
                    name="confirmPassword"
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.confirmPassword
                    })}
                    value={this.state.confirmPassword}
                    onChange={this.onChange}
                  />
                  <FieldErrors msgs={errors.confirmPassword} />
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
Register.propTypes = {
  createNewUser: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired,
  security: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  errors: state.errors,
  security: state.securityContainer
});

export default connect(
  mapStateToProps,
  { createNewUser }
)(Register);
