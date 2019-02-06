import jwt_decode from "jwt-decode";
import { SET_CURRENT_USER } from "../actions/types";
import store from "../store";

const setCurrentUser = jwtToken => {
  const decodedJwtToken = jwt_decode(jwtToken);
  store.dispatch({
    type: SET_CURRENT_USER,
    payload: decodedJwtToken
  });
  return decodedJwtToken;
};

export default setCurrentUser;
