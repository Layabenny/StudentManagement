package com.iplus.studentManagement.Controller;

import com.iplus.studentManagement.Entity.StudentEntity;
import com.iplus.studentManagement.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public String listStudents(Model model) {
        List<StudentEntity> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "studetns"; // Note: Typo in HTML filename
    }

    @GetMapping("/new")
    public String showStudentForm(Model model) {
        model.addAttribute("student", new StudentEntity());
        return "create_student";
    }

    @PostMapping
    public String saveStudent(@ModelAttribute StudentEntity student, RedirectAttributes redirectAttributes) {
        try {
            studentService.saveStudent(student);
            redirectAttributes.addFlashAttribute("success", "Student added successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/students/new";
        }
        return "redirect:/students";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        StudentEntity student = studentService.getStudentById(id)
            .orElseThrow(() -> new RuntimeException("Student not found"));
        model.addAttribute("student", student);
        return "edit_student";
    }

    @PostMapping("/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute StudentEntity student, 
                               RedirectAttributes redirectAttributes) {
        try {
            studentService.updateStudent(id, student);
            redirectAttributes.addFlashAttribute("success", "Student updated successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/students/edit/" + id;
        }
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            studentService.deleteStudent(id);
            redirectAttributes.addFlashAttribute("success", "Student deleted successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/students";
    }
}