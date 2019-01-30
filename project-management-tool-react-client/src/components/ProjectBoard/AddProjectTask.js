import React, { Component } from "react";
import { Link } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { createProjectTask } from "../../actions/projectBoardActions";
import classnames from "classnames";
import FieldErrors from "../Layout/FieldErrors";

class AddProjectTask extends Component {
  constructor(props) {
    super(props);
    const { projectCode } = this.props.match.params;
    this.state = {
      projectCode: projectCode,
      summary: "",
      acceptanceCriteria: "",
      status: "",
      priority: "",
      dueDate: "",
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

  // prepare a project task object from the state and send to createProject() function
  onSubmit(e) {
    e.preventDefault();
    const newProjectTask = {
      summary: this.state.summary,
      acceptanceCriteria: this.state.acceptanceCriteria,
      status: this.state.status,
      priority: this.state.priority,
      dueDate: this.state.dueDate
    };
    this.props.createProjectTask(
      this.state.projectCode,
      newProjectTask,
      this.props.history
    );
  }

  render() {
    const { errors } = this.state;
    const { projectCode } = this.state;
    return (
      <div className="add-PBI">
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <Link
                to={`/projectBoard/${projectCode}`}
                className="btn btn-light"
              >
                Back to Project Board
              </Link>
              <h4 className="display-4 text-center">Create Project Task</h4>
              <p className="lead text-center">Project {projectCode}</p>
            </div>
          </div>
          <div className="row">
            <div className="col-md-8 m-auto">
              <form onSubmit={this.onSubmit}>
                <div className="form-group">
                  <h6>Summary</h6>
                  <input
                    type="text"
                    name="summary"
                    placeholder="Project Task summary"
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.summary
                    })}
                    value={this.state.summary}
                    onChange={this.onChange}
                  />
                  <FieldErrors msgs={errors.summary} />
                </div>
                <div className="form-group">
                  <h6>Acceptance Criteria</h6>
                  <textarea
                    placeholder="Acceptance Criteria"
                    name="acceptanceCriteria"
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.acceptanceCriteria
                    })}
                    value={this.state.acceptanceCriteria}
                    onChange={this.onChange}
                  />
                  <FieldErrors msgs={errors.acceptanceCriteria} />
                </div>

                <div className="form-group">
                  <h6>Due Date</h6>
                  <input
                    type="date"
                    name="dueDate"
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.dueDate
                    })}
                    value={this.state.dueDate}
                    onChange={this.onChange}
                  />
                  <FieldErrors msgs={errors.dueDate} />
                </div>
                <div className="form-group">
                  <h6>Priority</h6>
                  <select
                    name="priority"
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.priority
                    })}
                    value={this.state.priority}
                    onChange={this.onChange}
                  >
                    <option value="">Select Priority</option>
                    <option value={1}>High</option>
                    <option value={2}>Medium</option>
                    <option value={3}>Low</option>
                  </select>
                  <FieldErrors msgs={errors.priority} />
                </div>

                <div className="form-group">
                  <h6>Status</h6>
                  <select
                    name="status"
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.status
                    })}
                    value={this.state.status}
                    onChange={this.onChange}
                  >
                    <option value="">Select Status</option>
                    <option value="TODO">TO DO</option>
                    <option value="IN PROGRESS">IN PROGRESS</option>
                    <option value="DONE">DONE</option>
                  </select>
                  <FieldErrors msgs={errors.status} />
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
AddProjectTask.propTypes = {
  createProjectTask: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired
};

// function to map state to properties
const mapStateToProps = state => ({
  errors: state.errors
});

// connect component to redux and export
export default connect(
  mapStateToProps,
  { createProjectTask }
)(AddProjectTask);
