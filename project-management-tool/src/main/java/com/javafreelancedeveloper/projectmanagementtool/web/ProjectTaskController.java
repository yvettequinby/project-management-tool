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

@RestController
@RequestMapping("api/project/{projectCode}/task")
@AllArgsConstructor
@CrossOrigin
public class ProjectTaskController extends BaseController {

    private final ProjectTaskService projectTaskService;

    @GetMapping("/list")
    public ResponseEntity<ProjectTaskListDTO> getProjectTaskList(@PathVariable String projectCode) {
        ProjectTaskListDTO projectTaskList = projectTaskService.getProjectTaskList(projectCode);
        return new ResponseEntity<>(projectTaskList, HttpStatus.OK);
    }

    @GetMapping("/{projectTaskCode}")
    public ResponseEntity<ProjectTaskDTO> getProjectTask(@PathVariable String projectCode,
                                                             @PathVariable String projectTaskCode) {
        ProjectTaskDTO projectTask = projectTaskService.findByProjectTaskCode(projectCode, projectTaskCode);
        return new ResponseEntity<>(projectTask, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ProjectTaskDTO> createProjectTask(@PathVariable String projectCode,
                                                                @Valid @RequestBody ProjectTaskLiteDTO projectTask,
                                                                BindingResult bindingResult) {
        validate(bindingResult);
        ProjectTaskDTO savedProjectTask = projectTaskService.saveProjectTask(projectCode, projectTask);
        return new ResponseEntity<>(savedProjectTask, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<ProjectTaskDTO> updateProjectTask(@PathVariable String projectCode,
                                                            @Valid @RequestBody ProjectTaskLiteDTO projectTask,
                                                            BindingResult bindingResult) {
        validate(bindingResult);
        ProjectTaskDTO savedProjectTask = projectTaskService.saveProjectTask(projectCode, projectTask);
        return new ResponseEntity<>(savedProjectTask, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{projectTaskCode}")
    public ResponseEntity<Void> deleteProject(@PathVariable String projectCode,
                                              @PathVariable String projectTaskCode) {
        projectTaskService.deleteProjectTask(projectCode, projectTaskCode);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
