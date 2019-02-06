import React from "react";

export default function ErrorList(props) {
  if (props.errors) {
    if (props.errors.message) {
      return <React.Fragment />;
    } else {
      // field errors
      const keys = Object.keys(props.errors);
      if (keys.length > 0) {
        return (
          <React.Fragment>
            <div className="row">
              <div className="col-md-12">
                <div class="alert alert-danger" role="alert">
                  Please correct the following errors:
                  <ul>
                    {keys.map(function(key, index) {
                      return props.errors[key].map(function(msg, index) {
                        return <li>{msg}</li>;
                      });
                    })}
                  </ul>
                </div>
              </div>
            </div>
          </React.Fragment>
        );
      } else {
        return <React.Fragment />;
      }
    }
  } else {
    return <React.Fragment />;
  }
}
