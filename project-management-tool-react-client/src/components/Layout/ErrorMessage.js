import React from "react";

export default function ErrorMessage(props) {
  if (props.errors && props.errors.message) {
    // generic error message
    return (
      <React.Fragment>
        <div className="row">
          <div className="col-md-12">
            <div class="alert alert-danger" role="alert">
              ERROR: {props.errors.message}
            </div>
          </div>
        </div>
      </React.Fragment>
    );
  } else {
    return <React.Fragment />;
  }
}
