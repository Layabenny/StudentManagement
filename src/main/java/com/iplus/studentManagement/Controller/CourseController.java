package com.iplus.studentManagement.Controller;

import com.iplus.studentManagement.Entity.CourseEntity;
import com.iplus.studentManagement.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public String listCourses(Model model) {
        List<CourseEntity> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        return "courses";
    }

    @GetMapping("/new")
    public String showCourseForm(Model model) {
        model.addAttribute("course", new CourseEntity());
        return "create_course";
    }

    @PostMapping
    public String saveCourse(@ModelAttribute CourseEntity course, RedirectAttributes redirectAttributes) {
        try {
            courseService.saveCourse(course);
            redirectAttributes.addFlashAttribute("success", "Course added successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/courses/new";
        }
        return "redirect:/courses";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        CourseEntity course = courseService.getCourseById(id)
            .orElseThrow(() -> new RuntimeException("Course not found"));
        model.addAttribute("course", course);
        return "edit_course";
    }

    @PostMapping("/{id}")
    public String updateCourse(@PathVariable Long id, @ModelAttribute CourseEntity course, 
                              RedirectAttributes redirectAttributes) {
        try {
            courseService.updateCourse(id, course);
            redirectAttributes.addFlashAttribute("success", "Course updated successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/courses/edit/" + id;
        }
        return "redirect:/courses";
    }

    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            courseService.deleteCourse(id);
            redirectAttributes.addFlashAttribute("success", "Course deleted successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/courses";
    }
}