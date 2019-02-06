import React, { Component } from "react";
import {
  getProjectTask,
  updateProjectTask
} from "../../actions/projectBoardActions";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import classnames from "classnames";
import FieldErrors from "../Layout/FieldErrors";
import { Link } from "react-router-dom";
import ErrorMessage from "../Layout/ErrorMessage";

class UpdateProjectTask extends Component {
  constructor(props) {
    super(props);
    const { projectCode } = this.props.match.params;
    const { projectTaskCode } = this.props.match.params;
    this.state = {
      project: {
        code: projectCode
      },
      id: "",
      code: projectTaskCode,
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
    const {
      project,
      id,
      code,
      summary,
      acceptanceCriteria,
      status,
      priority,
      dueDate
    } = nextProps.projectTask;

    this.setState({
      project,
      id,
      code,
      summary,
      acceptanceCriteria,
      status,
      priority,
      dueDate
    });
  }

  // prepare a project task object from the state and send to updateProjectTask() function
  onSubmit(e) {
    e.preventDefault();
    const updatedProjectTask = {
      id: this.state.id,
      code: this.state.code,
      summary: this.state.summary,
      acceptanceCriteria: this.state.acceptanceCriteria,
      status: this.state.status,
      priority: this.state.priority,
      dueDate: this.state.dueDate
    };
    this.props.updateProjectTask(
      this.state.project.code,
      updatedProjectTask,
      this.props.history
    );
  }

  // update state with any new form values
  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  componentDidMount() {
    this.props.getProjectTask(
      this.state.project.code,
      this.state.code,
      this.props.history
    );
  }

  render() {
    const { errors } = this.state;
    const { project } = this.state;
    return (
      <div className="add-PBI">
        <div className="container">
          <ErrorMessage errors={errors} />
          <div className="row">
            <div className="col-md-8 m-auto">
              <Link
                to={`/projectBoard/${project.code}`}
                className="btn btn-light"
              >
                Back to Project Board
              </Link>
              <h4 className="display-4 text-center">Update Project Task</h4>
              <p className="lead text-center">Project {project.code}</p>
            </div>
          </div>
          <div className="row">
            <div className="col-md-8 m-auto">
              <form onSubmit={this.onSubmit}>
                <div className="form-group">
                  <h6>Task Code</h6>
                  <input
                    type="text"
                    name="code"
                    disabled
                    value={this.state.code}
                    onChange={this.onChange}
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.code
                    })}
                  />
                  <FieldErrors msgs={errors.code} />
                </div>
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

UpdateProjectTask.propTypes = {
  getProjectTask: PropTypes.func.isRequired,
  projectTask: PropTypes.object.isRequired,
  updateProjectTask: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  projectTask: state.projectTaskContainer.projectTask,
  errors: state.errors
});

export default connect(
  mapStateToProps,
  { getProjectTask, updateProjectTask }
)(UpdateProjectTask);
