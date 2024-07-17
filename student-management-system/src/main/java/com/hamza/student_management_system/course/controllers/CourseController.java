package com.hamza.student_management_system.course.controllers;

import com.hamza.student_management_system.course.datamodels.CourseDto;
import com.hamza.student_management_system.course.facade.interfaces.CourseFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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
    public ResponseEntity<CourseDto> registerCourse(@PathVariable Long courseId){
        return ResponseEntity.ok(this.courseFacade.registerCourse(courseId));
    }

    @PutMapping("/{courseId}/cancel")
    public ResponseEntity<CourseDto> cancelCourseRegistration(@PathVariable Long courseId){
        return ResponseEntity.ok(this.courseFacade.cancelCourseRegistration(courseId));
    }

//    @GetMapping("/schedules/{courseId}/pdf")
//    public ResponseEntity<CourseDto> getCourseSchedule(@PathVariable Long courseId) throws IOException {
//
//    }
}
