import axios from "axios";
import { GET_ERRORS, SET_CURRENT_USER } from "./types";
import setJwtToken from "../utils/setJwtToken";
import setCurrentUser from "../utils/setCurrentUser";

export const createNewUser = (newUser, history) => async dispatch => {
  try {
    await axios.post("/api/user/register", newUser);
    history.push("/login");
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

export const login = loginRequest => async dispatch => {
  try {
    // post the login request
    const res = await axios.post("/api/user/login", loginRequest);
    // extract jwt token from response
    const { jwtToken } = res.data;
    // store the token in redux
    localStorage.setItem("jwtToken", jwtToken);
    // set our token in header for all future requests
    setJwtToken(jwtToken);
    // set the current user
    setCurrentUser(jwtToken);
  } catch (err) {
    if (err.response) {
      dispatch({
        type: GET_ERRORS,
        payload: err.response.data
      });
    } else {
      console.log(err);
    }
  }
};

export const logout = () => dispatch => {
  localStorage.removeItem("jwtToken");
  setJwtToken(false);
  dispatch({
    type: SET_CURRENT_USER,
    payload: {}
  });
};
