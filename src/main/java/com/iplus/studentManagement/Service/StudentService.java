package com.iplus.studentManagement.Service;

import com.iplus.studentManagement.Entity.StudentEntity;
import com.iplus.studentManagement.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public List<StudentEntity> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<StudentEntity> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public StudentEntity saveStudent(StudentEntity student) {
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new RuntimeException("Email already exists: " + student.getEmail());
        }
        return studentRepository.save(student);
    }

    public StudentEntity updateStudent(Long id, StudentEntity studentDetails) {
        StudentEntity student = studentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        
        if (!student.getEmail().equals(studentDetails.getEmail()) && 
            studentRepository.existsByEmail(studentDetails.getEmail())) {
            throw new RuntimeException("Email already exists: " + studentDetails.getEmail());
        }
        
        student.setFirstName(studentDetails.getFirstName());
        student.setLastName(studentDetails.getLastName());
        student.setEmail(studentDetails.getEmail());
        
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        StudentEntity student = studentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        studentRepository.delete(student);
    }

    public boolean existsByEmail(String email) {
        return studentRepository.existsByEmail(email);
    }
}
