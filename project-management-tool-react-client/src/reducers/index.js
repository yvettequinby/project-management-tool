import { combineReducers } from "redux";
import errorReducer from "./errorReducer";
import projectReducer from "./projectReducer";
import projectTaskReducer from "./projectTaskReducer";
import securityReducer from "./securityReducer";

export default combineReducers({
  errors: errorReducer,
  projectContainer: projectReducer,
  projectTaskContainer: projectTaskReducer,
  securityContainer: securityReducer
});
