package com.iplus.studentManagement.Service;

import com.iplus.studentManagement.Entity.EnrolmentEntity;
import com.iplus.studentManagement.Entity.StudentEntity;
import com.iplus.studentManagement.Entity.CourseEntity;
import com.iplus.studentManagement.Repository.EnrolmentRepository;
import com.iplus.studentManagement.Repository.StudentRepository;
import com.iplus.studentManagement.Repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EnrolmentService {
    @Autowired
    private EnrolmentRepository enrolmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    public List<EnrolmentEntity> getAllEnrollments() {
        return enrolmentRepository.findAll();
    }

    public Optional<EnrolmentEntity> getEnrolmentById(Long id) {
        return enrolmentRepository.findById(id);
    }

    public EnrolmentEntity saveEnrolment(EnrolmentEntity enrolment) {
        // Check if student exists
        StudentEntity student = studentRepository.findById(enrolment.getStudent().getId())
            .orElseThrow(() -> new RuntimeException("Student not found"));
        
        // Check if course exists
        CourseEntity course = courseRepository.findById(enrolment.getCourse().getId())
            .orElseThrow(() -> new RuntimeException("Course not found"));
        
        // Check if enrollment already exists
        if (enrolmentRepository.existsByStudentAndCourse(student, course)) {
            throw new RuntimeException("Student is already enrolled in this course");
        }
        
        enrolment.setStudent(student);
        enrolment.setCourse(course);
        
        return enrolmentRepository.save(enrolment);
    }

    public EnrolmentEntity updateEnrolment(Long id, EnrolmentEntity enrolmentDetails) {
        EnrolmentEntity enrolment = enrolmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Enrollment not found with id: " + id));
        
        enrolment.setGrade(enrolmentDetails.getGrade());
        
        return enrolmentRepository.save(enrolment);
    }

    public void deleteEnrolment(Long id) {
        EnrolmentEntity enrolment = enrolmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Enrollment not found with id: " + id));
        enrolmentRepository.delete(enrolment);
    }

    public List<EnrolmentEntity> getEnrollmentsByStudentId(Long studentId) {
        return enrolmentRepository.findEnrollmentsByStudentId(studentId);
    }

    public List<EnrolmentEntity> getEnrollmentsByCourseId(Long courseId) {
        return enrolmentRepository.findByCourseId(courseId);
    }
}