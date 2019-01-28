package com.javafreelancedeveloper.projectmanagementtool.repository;

import com.javafreelancedeveloper.projectmanagementtool.domain.ProjectTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {

    ProjectTask findByCode(String projectTaskCode);
}
