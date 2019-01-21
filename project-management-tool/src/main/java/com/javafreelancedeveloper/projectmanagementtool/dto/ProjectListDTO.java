package com.javafreelancedeveloper.projectmanagementtool.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProjectListDTO {

    private final List<ProjectDTO> project;
}
