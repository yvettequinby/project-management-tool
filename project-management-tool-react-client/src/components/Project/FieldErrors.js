import React from "react";

function FieldErrors(props) {
  if (props.msgs) {
    return (
      <React.Fragment>
        {props.msgs.map(function(msg, index) {
          return <div className="invalid-feedback">{msg}</div>;
        })}
      </React.Fragment>
    );
  } else {
    return <React.Fragment />;
  }
}

export default FieldErrors;
