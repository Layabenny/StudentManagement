package com.iplus.studentManagement.Controller;

import com.iplus.studentManagement.Entity.EnrolmentEntity;
import com.iplus.studentManagement.Entity.StudentEntity;
import com.iplus.studentManagement.Entity.CourseEntity;
import com.iplus.studentManagement.Service.EnrolmentService;
import com.iplus.studentManagement.Service.StudentService;
import com.iplus.studentManagement.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Arrays;

@Controller
@RequestMapping("/enrollments")
public class EnrolmentController {

    @Autowired
    private EnrolmentService enrolmentService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    private final List<String> GRADE_OPTIONS = Arrays.asList("A", "B", "C", "D", "F", "Pass", "Fail");

    @GetMapping
    public String listEnrollments(Model model) {
        List<EnrolmentEntity> enrollments = enrolmentService.getAllEnrollments();
        model.addAttribute("enrollments", enrollments);
        return "enrollments";
    }

    @GetMapping("/new")
    public String showEnrollmentForm(Model model) {
        model.addAttribute("enrollment", new EnrolmentEntity());
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("gradeOptions", GRADE_OPTIONS);
        return "create_enrollment";
    }

    @PostMapping
    public String saveEnrollment(@RequestParam Long studentId, 
                                @RequestParam Long courseId,
                                @RequestParam(required = false) String grade,
                                RedirectAttributes redirectAttributes) {
        try {
            StudentEntity student = studentService.getStudentById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
            CourseEntity course = courseService.getCourseById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
            
            EnrolmentEntity enrollment = new EnrolmentEntity(student, course, grade);
            enrolmentService.saveEnrolment(enrollment);
            redirectAttributes.addFlashAttribute("success", "Enrollment added successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/enrollments/new";
        }
        return "redirect:/enrollments";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        EnrolmentEntity enrollment = enrolmentService.getEnrolmentById(id)
            .orElseThrow(() -> new RuntimeException("Enrollment not found"));
        
        model.addAttribute("enrollment", enrollment);
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("gradeOptions", GRADE_OPTIONS);
        
        return "edit_enrollment";
    }

    @PostMapping("/update/{id}")
    public String updateEnrollment(@PathVariable Long id,
                                  @RequestParam Long studentId,
                                  @RequestParam Long courseId,
                                  @RequestParam(required = false) String grade,
                                  RedirectAttributes redirectAttributes) {
        try {
            EnrolmentEntity enrollment = enrolmentService.getEnrolmentById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));
            
            enrollment.setGrade(grade);
            enrolmentService.updateEnrolment(id, enrollment);
            redirectAttributes.addFlashAttribute("success", "Enrollment updated successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/enrollments/edit/" + id;
        }
        return "redirect:/enrollments";
    }

    @GetMapping("/delete/{id}")
    public String deleteEnrollment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            enrolmentService.deleteEnrolment(id);
            redirectAttributes.addFlashAttribute("success", "Enrollment deleted successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/enrollments";
    }

    @GetMapping("/{id}")
    public String viewEnrollment(@PathVariable Long id, Model model) {
        EnrolmentEntity enrollment = enrolmentService.getEnrolmentById(id)
            .orElseThrow(() -> new RuntimeException("Enrollment not found"));
        model.addAttribute("enrollment", enrollment);
        return "enrollment_details";
    }

    @GetMapping("/student/{id}")
    public String getStudentEnrollments(@PathVariable Long id, Model model) {
        List<EnrolmentEntity> enrollments = enrolmentService.getEnrollmentsByStudentId(id);
        StudentEntity student = studentService.getStudentById(id)
            .orElseThrow(() -> new RuntimeException("Student not found"));
        
        model.addAttribute("enrollments", enrollments);
        model.addAttribute("student", student);
        return "student_enrollments";
    }
}