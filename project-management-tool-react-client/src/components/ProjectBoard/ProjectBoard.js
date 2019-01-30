import React, { Component } from "react";

import { connect } from "react-redux";
import { getProjectTasks } from "../../actions/projectBoardActions";
import PropTypes from "prop-types";
import ProjectTaskList from "./ProjectTaskList";
import ProjectBoardHeader from "./ProjectBoardHeader";
import ErrorList from "../Layout/ErrorList";

class ProjectBoard extends Component {
  constructor() {
    super();
    this.state = {
      errors: {}
    };
  }

  // load the projects when this component mounts
  componentDidMount() {
    const { projectCode } = this.props.match.params;
    this.props.getProjectTasks(projectCode);
  }

  // life cycle hooks
  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
  }

  render() {
    const { projectTaskList } = this.props;
    const { errors } = this.props;
    const { projectCode } = this.props.match.params;

    return (
      <div className="container">
        <ProjectBoardHeader
          projectTaskList={projectTaskList}
          projectCode={projectCode}
        />

        <br />
        <hr />

        <div className="container">
          <ErrorList errors={errors} />
          <div className="row">
            <div className="col-md-4">
              <div className="card text-center mb-2">
                <div className="card-header bg-secondary text-white">
                  <h3>TO DO</h3>
                </div>
              </div>
              <ProjectTaskList
                projectTaskList={projectTaskList}
                status="TODO"
              />
            </div>
            <div className="col-md-4">
              <div className="card text-center mb-2">
                <div className="card-header bg-primary text-white">
                  <h3>In Progress</h3>
                </div>
              </div>
              <ProjectTaskList
                projectTaskList={projectTaskList}
                status="IN PROGRESS"
              />
            </div>
            <div className="col-md-4">
              <div className="card text-center mb-2">
                <div className="card-header bg-success text-white">
                  <h3>Done</h3>
                </div>
              </div>
              <ProjectTaskList
                projectTaskList={projectTaskList}
                status="DONE"
              />
            </div>
          </div>
        </div>
      </div>
    );
  }
}

ProjectBoard.propTypes = {
  getProjectTasks: PropTypes.func.isRequired,
  projectTaskList: PropTypes.object.isRequired,
  errors: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  projectTaskList: state.projectTaskContainer.projectTaskList,
  errors: state.errors
});

export default connect(
  mapStateToProps,
  { getProjectTasks }
)(ProjectBoard);
