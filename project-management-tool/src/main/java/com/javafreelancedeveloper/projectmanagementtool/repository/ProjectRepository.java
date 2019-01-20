package com.javafreelancedeveloper.projectmanagementtool.repository;

import com.javafreelancedeveloper.projectmanagementtool.domain.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {


}
