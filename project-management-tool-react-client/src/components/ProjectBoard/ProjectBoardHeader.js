import React from "react";
import { Link } from "react-router-dom";

function NoTasksInfo(props) {
  if (
    props.projectTaskList.projectTasks &&
    props.projectTaskList.projectTasks.length > 0
  ) {
    return null;
  } else {
    return (
      <React.Fragment>
        <br />
        <hr />
        <div className="row">
          <div className="col-md-12">
            <div className="alert alert-info">
              No tasks exist yet for this project
            </div>
          </div>
        </div>
      </React.Fragment>
    );
  }
}

export default function ProjectBoardHeader(props) {
  const { projectTaskList } = props;
  const { projectCode } = props;
  if (projectTaskList.project) {
    return (
      <React.Fragment>
        <div className="row">
          <div className="col-md-4">
            <Link
              to={`/addProjectTask/${projectCode}`}
              className="btn btn-primary mb-3"
            >
              <i className="fas fa-plus-circle" />
              &nbsp; Create Project Task
            </Link>
          </div>
          <div className="col-md-8">
            <h3>
              Project {projectTaskList.project.code} -{" "}
              {projectTaskList.project.name}
            </h3>
          </div>
        </div>
        <NoTasksInfo projectTaskList={projectTaskList} />
      </React.Fragment>
    );
  } else {
    return <React.Fragment />;
  }
}
