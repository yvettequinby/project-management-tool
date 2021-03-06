import { SET_CURRENT_USER } from "../actions/types";

const initialState = {
  validToken: false,
  user: {}
};

const isTokenPresent = payload => {
  if (payload) {
    return true;
  } else {
    return false;
  }
};

export default function(state = initialState, action) {
  switch (action.type) {
    case SET_CURRENT_USER:
      return {
        ...state,
        validToken: isTokenPresent(action.payload),
        user: action.payload
      };

    default:
      return state;
  }
}
