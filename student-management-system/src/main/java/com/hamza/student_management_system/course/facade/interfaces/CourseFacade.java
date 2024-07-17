package com.hamza.student_management_system.course.facade.interfaces;

import com.hamza.student_management_system.course.datamodels.CourseDto;

import java.util.List;

public interface CourseFacade {
    List<CourseDto> findAll();
    List<CourseDto> findAllByUserId();
    CourseDto findById(Long id);
}
