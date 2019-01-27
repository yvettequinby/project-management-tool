import React, { Component } from "react";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { createProject } from "../../actions/projectActions";
import classnames from "classnames";
import FieldErrors from "./FieldErrors";

class AddProject extends Component {
  constructor() {
    super();
    this.state = {
      name: "",
      code: "",
      description: "",
      startDate: "",
      endDate: "",
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

  // update state with any new form values
  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  // prepare a project object from the state and send to createProject() function
  onSubmit(e) {
    e.preventDefault();
    const newProject = {
      name: this.state.name,
      code: this.state.code,
      description: this.state.description,
      startDate: this.state.startDate,
      endDate: this.state.endDate
    };
    this.props.createProject(newProject, this.props.history);
  }

  render() {
    const { errors } = this.state;
    return (
      <div className="project">
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <h5 className="display-4 text-center">Create Project</h5>
              <hr />
              <form onSubmit={this.onSubmit}>
                <div className="form-group">
                  <input
                    type="text"
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.name
                    })}
                    placeholder="Project Name"
                    name="name"
                    value={this.state.name}
                    onChange={this.onChange}
                  />
                  <FieldErrors msgs={errors.name} />
                </div>
                <div className="form-group">
                  <input
                    type="text"
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.code
                    })}
                    placeholder="Unique Project Code"
                    name="code"
                    value={this.state.code}
                    onChange={this.onChange}
                  />
                  <FieldErrors msgs={errors.code} />
                </div>
                <div className="form-group">
                  <textarea
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.description
                    })}
                    placeholder="Project Description"
                    name="description"
                    value={this.state.description}
                    onChange={this.onChange}
                  />
                  <FieldErrors msgs={errors.description} />
                </div>
                <h6>Start Date</h6>
                <div className="form-group">
                  <input
                    type="date"
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.startDate
                    })}
                    name="startDate"
                    value={this.state.startDate}
                    onChange={this.onChange}
                  />
                  <FieldErrors msgs={errors.startDate} />
                </div>
                <h6>Estimated End Date</h6>
                <div className="form-group">
                  <input
                    type="date"
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.endDate
                    })}
                    name="endDate"
                    value={this.state.endDate}
                    onChange={this.onChange}
                  />
                  <FieldErrors msgs={errors.endDate} />
                </div>

                <input
                  type="submit"
                  className="btn btn-primary btn-block mt-4"
                />
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

// Add some restraints so the component is always used properly
AddProject.propTypes = {
  createProject: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired
};

// function to map state to properties
const mapStateToProps = state => ({
  errors: state.errors
});

// connect component to redux and export
export default connect(
  mapStateToProps,
  { createProject }
)(AddProject);
