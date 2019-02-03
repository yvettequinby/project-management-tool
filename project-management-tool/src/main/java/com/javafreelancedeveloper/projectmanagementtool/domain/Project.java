package com.javafreelancedeveloper.projectmanagementtool.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(updatable = false, unique = true)
    private String code;
    private String description;
    private Date startDate;
    private Date endDate;
    private Date createdTimestamp;
    private Date updatedTimestamp;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "project")
    private List<ProjectTask> projectTasks = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

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
