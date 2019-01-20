package com.javafreelancedeveloper.projectmanagementtool.domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String projectName;
    @Column(updatable = false, unique = true)
    private String projectIdentifier;
    private String description;
    private Date startDate;
    private Date endDate;
    private Date createdTimestamp;
    private Date updatedTimestamp;

    @PrePersist
    public void onCreate() {
        createdTimestamp = new Date();
        updatedTimestamp = new Date();
    }

    @PreUpdate
    public void onUpdate() {
        updatedTimestamp = new Date();
    }
}
