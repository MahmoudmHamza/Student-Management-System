package com.hamza.student_management_system.course.facade;

import com.hamza.student_management_system.course.datamodels.CourseDto;
import com.hamza.student_management_system.course.facade.interfaces.CourseFacade;
import com.hamza.student_management_system.course.mapper.interfaces.CourseMapper;
import com.hamza.student_management_system.course.services.interfaces.CourseService;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CourseFacadeImpl implements CourseFacade {

    private final CourseMapper courseMapper;
    private final CourseService courseService;

    @Override
    public List<CourseDto> findAll() {
        return this.courseService.findAllCourses().stream()
                .map(this.courseMapper::mapCourseToCourseDto)
                .toList();
    }

    @Override
    public List<CourseDto> findAllByUserId() {
        return this.courseService.findCoursesByUserId().stream()
                .map(this.courseMapper::mapCourseToCourseDto)
                .toList();
    }

    @Override
    public CourseDto findById(Long id) {
        return this.courseMapper.mapCourseToCourseDto(this.courseService.findCourseById(id));
    }
}
