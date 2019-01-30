import React from "react";
import ProjectTask from "./ProjectTask";

function ProjectTaskList(props) {
  const { projectTaskList } = props;
  const { status } = props;
  if (projectTaskList.projectTasks) {
    const filteredTasks = projectTaskList.projectTasks.filter(
      projectTask => projectTask.status === status
    );
    return (
      <React.Fragment>
        {filteredTasks.map(projectTask => (
          <ProjectTask
            key={projectTask.id}
            projectTask={projectTask}
            project={projectTaskList.project}
          />
        ))}
      </React.Fragment>
    );
  } else {
    return <React.Fragment />;
  }
}

export default ProjectTaskList;
