package com.javafreelancedeveloper.projectmanagementtool.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(updatable = false, unique = true)
    private String code;
    @Column(updatable = false, unique = true)
    private Integer sequence;
    private String summary;
    private String acceptanceCriteria;
    private String status;
    private Integer priority;
    private Date dueDate;
    private Date createdTimestamp;
    private Date updatedTimestamp;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "project_id", nullable = false, updatable = false)
    private Project project;

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
