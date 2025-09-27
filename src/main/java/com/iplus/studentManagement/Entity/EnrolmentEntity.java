package com.iplus.studentManagement.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "enrollments", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"student_id", "course_id"})
})
public class EnrolmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private StudentEntity student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private CourseEntity course;

    private String grade;

    // Constructors
    public EnrolmentEntity() {}

    public EnrolmentEntity(StudentEntity student, CourseEntity course, String grade) {
        this.student = student;
        this.course = course;
        this.grade = grade;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public StudentEntity getStudent() { return student; }
    public void setStudent(StudentEntity student) { this.student = student; }

    public CourseEntity getCourse() { return course; }
    public void setCourse(CourseEntity course) { this.course = course; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
}