package com.hamza.student_management_system.course.controllers;

import com.hamza.student_management_system.course.datamodels.CourseDto;
import com.hamza.student_management_system.course.facade.interfaces.CourseFacade;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseFacade courseFacade;

    @GetMapping
    public ResponseEntity<List<CourseDto>> getAllCourse() {
        List<CourseDto> coursesList = this.courseFacade.findAll();
        return ResponseEntity.ok(coursesList);
    }

    @GetMapping("/view")
    public ResponseEntity<List<CourseDto>> viewStudentCourse() {
        List<CourseDto> coursesList = this.courseFacade.findAllByUserId();
        return ResponseEntity.ok(coursesList);
    }

    @GetMapping("/details/{courseId}")
    public ResponseEntity<CourseDto> findById(@PathVariable Long courseId) {
        return ResponseEntity.ok(this.courseFacade.findById(courseId));
    }

    @PostMapping("/{courseId}/register")
    public ResponseEntity<CourseDto> registerCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(this.courseFacade.registerCourse(courseId));
    }

    @DeleteMapping("/{courseId}/cancel")
    public ResponseEntity<?> cancelCourseRegistration(@PathVariable Long courseId) {
        this.courseFacade.cancelCourseRegistration(courseId);
        return ResponseEntity.status(HttpStatus.OK).body("Course registration cancelled successfully");
    }

    @GetMapping("/schedule/{courseId}/pdf")
    public ResponseEntity<?> getCourseSchedule(HttpServletResponse response, @PathVariable Long courseId) throws IOException {
        this.courseFacade.getCourseSchedulePdf(response, courseId);
        return ResponseEntity.status(HttpStatus.OK).body("Course Schedule PDF generated successfully");
    }
}
