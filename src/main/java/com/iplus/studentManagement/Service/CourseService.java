package com.iplus.studentManagement.Service;

import com.iplus.studentManagement.Entity.CourseEntity;
import com.iplus.studentManagement.Repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public List<CourseEntity> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<CourseEntity> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public CourseEntity saveCourse(CourseEntity course) {
        if (courseRepository.existsByCourseName(course.getCourseName())) {
            throw new RuntimeException("Course name already exists: " + course.getCourseName());
        }
        return courseRepository.save(course);
    }

    public CourseEntity updateCourse(Long id, CourseEntity courseDetails) {
        CourseEntity course = courseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
        
        if (!course.getCourseName().equals(courseDetails.getCourseName()) && 
            courseRepository.existsByCourseName(courseDetails.getCourseName())) {
            throw new RuntimeException("Course name already exists: " + courseDetails.getCourseName());
        }
        
        course.setCourseName(courseDetails.getCourseName());
        course.setDescription(courseDetails.getDescription());
        
        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        CourseEntity course = courseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
        courseRepository.delete(course);
    }
}