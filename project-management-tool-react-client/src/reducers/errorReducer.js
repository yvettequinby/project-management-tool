import { GET_ERRORS } from "../actions/types";

const initialState = {};

export default function(state = initialState, action) {
  switch (action.type) {
    case GET_ERRORS:
      if (
        Object.prototype.toString.call(action.payload) === "[object String]"
      ) {
        return { message: action.payload };
      } else {
        return action.payload;
      }
    default:
      return state;
  }
}
