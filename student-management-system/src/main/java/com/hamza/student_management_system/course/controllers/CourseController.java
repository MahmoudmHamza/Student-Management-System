package com.hamza.student_management_system.course.controllers;

import com.hamza.student_management_system.course.datamodels.CourseDto;
import com.hamza.student_management_system.course.facade.interfaces.CourseFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    //register course
    //cancel registeration
    //get course scheule as pdf
}
