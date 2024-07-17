package com.hamza.student_management_system.course.mapper.interfaces;

import com.hamza.student_management_system.course.datamodels.CourseDto;
import com.hamza.student_management_system.course.entities.Course;

public interface CourseMapper {
    Course mapCourseDtoToCourse(CourseDto courseDto);
    CourseDto mapCourseToCourseDto(Course course);
}
