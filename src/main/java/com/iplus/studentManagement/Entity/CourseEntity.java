package com.iplus.studentManagement.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Course name is required")
    @Column(name = "course_name", unique = true, nullable = false)
    private String courseName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EnrolmentEntity> enrollments = new ArrayList<>();

    // Constructors
    public CourseEntity() {}

    public CourseEntity(String courseName, String description) {
        this.courseName = courseName;
        this.description = description;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<EnrolmentEntity> getEnrollments() { return enrollments; }
    public void setEnrollments(List<EnrolmentEntity> enrollments) { this.enrollments = enrollments; }
}