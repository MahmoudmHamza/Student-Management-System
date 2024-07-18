package com.hamza.student_management_system.course.facade.interfaces;

import com.hamza.student_management_system.course.datamodels.CourseDto;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public interface CourseFacade {
    List<CourseDto> findAll();
    List<CourseDto> findAllByUserId();
    CourseDto findById(Long id);
    CourseDto registerCourse(Long courseId);
    CourseDto cancelCourseRegistration(Long courseId);
    void getCourseSchedulePdf(HttpServletResponse response, Long courseId) throws IOException;
}
