import React, { Component } from "react";
import { Link } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { deleteProjectTask } from "../../actions/projectBoardActions";

class ProjectTask extends Component {
  onDeleteClick = (projectCode, projectTaskCode) => {
    this.props.deleteProjectTask(projectCode, projectTaskCode);
  };
  render() {
    const { projectTask } = this.props;
    const { project } = this.props;

    let priorityString;
    let priorityClass;
    if (projectTask.priority === 1) {
      priorityString = "High";
      priorityClass = "bg-danger text-light";
    } else if (projectTask.priority === 2) {
      priorityString = "Medium";
      priorityClass = "bg-warning text-light";
    } else {
      priorityString = "Low";
      priorityClass = "bg-info text-light";
    }

    return (
      <div className="card mb-1 bg-light">
        <div className={`card-header text-primary ${priorityClass}`}>
          {projectTask.code} ({priorityString})
        </div>
        <div className="card-body bg-light">
          <h5 className="card-title">{projectTask.summary}</h5>
          <p className="card-text text-truncate ">
            {projectTask.acceptanceCriteria}
          </p>
          <Link
            to={`/updateProjectTask/${project.code}/${projectTask.code}`}
            className="btn btn-primary"
          >
            View / Update
          </Link>

          <button
            className="btn btn-danger ml-4"
            onClick={this.onDeleteClick.bind(
              this,
              project.code,
              projectTask.code
            )}
          >
            Delete
          </button>
        </div>
      </div>
    );
  }
}

ProjectTask.propTypes = {
  deleteProjectTask: PropTypes.func.isRequired
};

export default connect(
  null,
  { deleteProjectTask }
)(ProjectTask);
