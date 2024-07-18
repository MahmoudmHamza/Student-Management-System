package com.hamza.student_management_system.course.services.interfaces;

import com.hamza.student_management_system.course.entities.Course;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public interface CourseService {
    List<Course> findAllCourses();
    List<Course> findCoursesByUserId();
    Course findCourseById(Long id);
    Course registerCourse(Long courseId);
    Course cancelCourseRegistration(Long courseId);
    void getCourseSchedulePdf(HttpServletResponse response, Long courseId) throws IOException;
}
