import axios from "axios";
import {
  GET_ERRORS,
  GET_PROJECT_TASKS,
  GET_PROJECT_TASK,
  DELETE_PROJECT_TASK
} from "./types";

export const createProjectTask = (
  projectCode,
  projectTask,
  history
) => async dispatch => {
  try {
    await axios.post(`/api/project/${projectCode}/task`, projectTask);
    history.push(`/projectBoard/${projectCode}`);
    dispatch({ type: GET_ERRORS, payload: {} });
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data
    });
  }
};

export const updateProjectTask = (
  projectCode,
  projectTask,
  history
) => async dispatch => {
  try {
    await axios.put(`/api/project/${projectCode}/task`, projectTask);
    history.push(`/projectBoard/${projectCode}`);
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

export const getProjectTasks = projectCode => async dispatch => {
  try {
    const res = await axios.get(`/api/project/${projectCode}/task/list`);
    dispatch({
      type: GET_PROJECT_TASKS,
      payload: res.data
    });
  } catch (err) {
    dispatch({ type: GET_ERRORS, payload: err.response.data });
  }
};

export const getProjectTask = (
  projectCode,
  projectTaskCode,
  history
) => async dispatch => {
  try {
    const res = await axios.get(
      `/api/project/${projectCode}/task/${projectTaskCode}`
    );
    dispatch({
      type: GET_PROJECT_TASK,
      payload: res.data
    });
  } catch (error) {
    // push the user back to the dashboard if there's an error
    history.push(`/projectBoard/${projectCode}`);
  }
};

export const deleteProjectTask = (
  projectCode,
  projectTaskCode
) => async dispatch => {
  if (window.confirm("Are you sure you want to delete this project task?")) {
    await axios.delete(`/api/project/${projectCode}/task/${projectTaskCode}`);
    const res = await axios.get(`/api/project/${projectCode}/task/list`);
    dispatch({ type: DELETE_PROJECT_TASK, payload: res.data });
  }
};
