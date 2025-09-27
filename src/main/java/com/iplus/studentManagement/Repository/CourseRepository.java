package com.iplus.studentManagement.Repository;

import com.iplus.studentManagement.Entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<CourseEntity, Long> {
    Optional<CourseEntity> findByCourseName(String courseName);
    boolean existsByCourseName(String courseName);
}
