package com.javafreelancedeveloper.projectmanagementtool.web;


import com.javafreelancedeveloper.projectmanagementtool.dto.ProjectTaskDTO;
import com.javafreelancedeveloper.projectmanagementtool.dto.ProjectTaskLiteDTO;
import com.javafreelancedeveloper.projectmanagementtool.dto.ProjectTaskListDTO;
import com.javafreelancedeveloper.projectmanagementtool.service.ProjectTaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("api/project/{projectCode}/task")
@AllArgsConstructor
@CrossOrigin
public class ProjectTaskController extends BaseController {

    private final ProjectTaskService projectTaskService;

    @GetMapping("/list")
    public ResponseEntity<ProjectTaskListDTO> getProjectTaskList(@PathVariable String projectCode,
                                                                 Principal principal) {
        ProjectTaskListDTO projectTaskList = projectTaskService.getProjectTaskList(projectCode, principal.getName());
        return new ResponseEntity<>(projectTaskList, HttpStatus.OK);
    }

    @GetMapping("/{projectTaskCode}")
    public ResponseEntity<ProjectTaskDTO> getProjectTask(@PathVariable String projectCode,
                                                         @PathVariable String projectTaskCode,
                                                         Principal principal) {
        ProjectTaskDTO projectTask = projectTaskService.findByProjectTaskCode(projectCode, projectTaskCode, principal.getName());
        return new ResponseEntity<>(projectTask, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ProjectTaskDTO> createProjectTask(@PathVariable String projectCode,
                                                            @Valid @RequestBody ProjectTaskLiteDTO projectTask,
                                                            BindingResult bindingResult,
                                                            Principal principal) {
        validate(bindingResult);
        ProjectTaskDTO savedProjectTask = projectTaskService.saveProjectTask(projectCode, projectTask, principal.getName());
        return new ResponseEntity<>(savedProjectTask, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<ProjectTaskDTO> updateProjectTask(@PathVariable String projectCode,
                                                            @Valid @RequestBody ProjectTaskLiteDTO projectTask,
                                                            BindingResult bindingResult,
                                                            Principal principal) {
        validate(bindingResult);
        ProjectTaskDTO savedProjectTask = projectTaskService.saveProjectTask(projectCode, projectTask, principal.getName());
        return new ResponseEntity<>(savedProjectTask, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{projectTaskCode}")
    public ResponseEntity<Void> deleteProject(@PathVariable String projectCode,
                                              @PathVariable String projectTaskCode,
                                              Principal principal) {
        projectTaskService.deleteProjectTask(projectCode, projectTaskCode, principal.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
