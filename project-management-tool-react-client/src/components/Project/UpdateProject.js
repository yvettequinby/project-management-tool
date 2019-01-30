import React, { Component } from "react";
import { getProject, updateProject } from "../../actions/projectActions";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import classnames from "classnames";
import FieldErrors from "../Layout/FieldErrors";

class UpdateProject extends Component {
  constructor() {
    super();
    this.state = {
      id: "",
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
    const {
      id,
      name,
      code,
      description,
      startDate,
      endDate
    } = nextProps.project;

    this.setState({
      id,
      name,
      code,
      description,
      startDate,
      endDate
    });
  }

  // prepare a project object from the state and send to createProject() function
  onSubmit(e) {
    e.preventDefault();
    const updatedProject = {
      id: this.state.id,
      name: this.state.name,
      code: this.state.code,
      description: this.state.description,
      startDate: this.state.startDate,
      endDate: this.state.endDate
    };
    this.props.updateProject(updatedProject, this.props.history);
  }

  // update state with any new form values
  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  componentDidMount() {
    const { projectCode } = this.props.match.params;
    this.props.getProject(projectCode, this.props.history);
  }

  render() {
    const { errors } = this.state;
    return (
      <div className="project">
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <h5 className="display-4 text-center">Update Project</h5>
              <hr />

              <form onSubmit={this.onSubmit}>
                <div className="form-group">
                  <h6>Name</h6>
                  <input
                    type="text"
                    name="name"
                    placeholder="Project Name"
                    value={this.state.name}
                    onChange={this.onChange}
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.name
                    })}
                  />
                  <FieldErrors msgs={errors.name} />
                </div>
                <div className="form-group">
                  <h6>Project Code</h6>
                  <input
                    type="text"
                    placeholder="Unique Project Code"
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
                  <h6>Description</h6>
                  <textarea
                    placeholder="Project Description"
                    name="description"
                    value={this.state.description}
                    onChange={this.onChange}
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.description
                    })}
                  />
                  <FieldErrors msgs={errors.description} />
                </div>

                <div className="form-group">
                  <h6>Start Date</h6>
                  <input
                    type="date"
                    name="startDate"
                    value={this.state.startDate}
                    onChange={this.onChange}
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.startDate
                    })}
                  />
                  <FieldErrors msgs={errors.startDate} />
                </div>

                <div className="form-group">
                  <h6>Estimated End Date</h6>
                  <input
                    type="date"
                    name="endDate"
                    value={this.state.endDate}
                    onChange={this.onChange}
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.endDate
                    })}
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

UpdateProject.propTypes = {
  getProject: PropTypes.func.isRequired,
  project: PropTypes.object.isRequired,
  updateProject: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  project: state.projectContainer.project,
  errors: state.errors
});

export default connect(
  mapStateToProps,
  { getProject, updateProject }
)(UpdateProject);
