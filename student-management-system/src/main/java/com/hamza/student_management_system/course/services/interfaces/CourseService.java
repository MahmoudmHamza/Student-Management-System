package com.hamza.student_management_system.course.services.interfaces;

import com.hamza.student_management_system.course.entities.Course;

import java.util.List;

public interface CourseService {
    List<Course> findAllCourses();
    List<Course> findCoursesByUserId();
    Course findCourseById(Long id);

    Course registerCourse(Long courseId);
    Course cancelCourseRegistration(Long courseId);
}
