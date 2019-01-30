import {
  GET_PROJECT_TASKS,
  GET_PROJECT_TASK,
  DELETE_PROJECT_TASK
} from "../actions/types";

const initialState = {
  projectTaskList: {},
  projectTask: {}
};

export default function(state = initialState, action) {
  switch (action.type) {
    case GET_PROJECT_TASKS:
      return {
        ...state,
        projectTaskList: action.payload
      };
    case GET_PROJECT_TASK:
      return { ...state, projectTask: action.payload };
    case DELETE_PROJECT_TASK:
      return { ...state, projectTaskList: action.payload };
    // TODO
    default:
      return state;
  }
}
