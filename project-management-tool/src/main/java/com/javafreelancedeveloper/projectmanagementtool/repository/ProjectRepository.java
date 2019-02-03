package com.javafreelancedeveloper.projectmanagementtool.repository;

import com.javafreelancedeveloper.projectmanagementtool.domain.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

    Project findByCode(String projectCode);

    List<Project> findByUser_Username(String username);

}
