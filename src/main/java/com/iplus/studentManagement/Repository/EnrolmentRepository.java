package com.iplus.studentManagement.Repository;

import com.iplus.studentManagement.Entity.EnrolmentEntity;
import com.iplus.studentManagement.Entity.StudentEntity;
import com.iplus.studentManagement.Entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface EnrolmentRepository extends JpaRepository<EnrolmentEntity, Long> {
    List<EnrolmentEntity> findByStudentId(Long studentId);
    List<EnrolmentEntity> findByCourseId(Long courseId);
    Optional<EnrolmentEntity> findByStudentAndCourse(StudentEntity student, CourseEntity course);
    boolean existsByStudentAndCourse(StudentEntity student, CourseEntity course);
    
    @Query("SELECT e FROM EnrolmentEntity e WHERE e.student.id = :studentId")
    List<EnrolmentEntity> findEnrollmentsByStudentId(@Param("studentId") Long studentId);
}