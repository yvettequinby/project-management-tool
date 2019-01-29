package com.javafreelancedeveloper.projectmanagementtool.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectTaskDTO extends ProjectTaskLiteDTO {

    private ProjectDTO project;

}
