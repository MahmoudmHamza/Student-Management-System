package com.hamza.student_management_system.course.mapper;

import com.hamza.student_management_system.course.datamodels.CourseDto;
import com.hamza.student_management_system.course.entities.Course;
import com.hamza.student_management_system.course.mapper.interfaces.CourseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseMapperImpl implements CourseMapper {
    @Override
    public Course mapCourseDtoToCourse(CourseDto courseDto) {
        return Course.builder()
                .courseCode(courseDto.getCourseCode())
                .courseName(courseDto.getCourseName())
                .description(courseDto.getDescription())
                .credits(courseDto.getCredits())
                .build();
    }

    @Override
    public CourseDto mapCourseToCourseDto(Course course) {
        return CourseDto.builder()
                .courseCode(course.getCourseCode())
                .courseName(course.getCourseName())
                .description(course.getDescription())
                .credits(course.getCredits())
                .build();
    }
}
