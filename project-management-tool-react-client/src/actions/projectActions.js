import axios from "axios";
import { GET_ERRORS, GET_PROJECTS, GET_PROJECT, DELETE_PROJECT } from "./types";

export const createProject = (project, history) => async dispatch => {
  try {
    await axios.post("/api/project", project);
    history.push("/dashboard");
    dispatch({ type: GET_ERRORS, payload: {} });
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data
    });
  }
};

export const updateProject = (project, history) => async dispatch => {
  try {
    await axios.put("/api/project", project);
    history.push("/dashboard");
    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data
    });
  }
};

export const getProjects = () => async dispatch => {
  const res = await axios.get("/api/project/list");
  dispatch({
    type: GET_PROJECTS,
    payload: res.data
  });
};

export const getProject = (projectCode, history) => async dispatch => {
  try {
    const res = await axios.get(`/api/project/${projectCode}`);
    dispatch({
      type: GET_PROJECT,
      payload: res.data
    });
  } catch (error) {
    // push the user back to the dashboard if there's an error
    history.push("/dashboard");
  }
};

export const deleteProject = projectCode => async dispatch => {
  if (
    window.confirm(
      "Are you sure you want to delete this project and all the related tasks?"
    )
  ) {
    await axios.delete(`/api/project/${projectCode}`);
    dispatch({ type: DELETE_PROJECT, payload: projectCode });
  }
};
