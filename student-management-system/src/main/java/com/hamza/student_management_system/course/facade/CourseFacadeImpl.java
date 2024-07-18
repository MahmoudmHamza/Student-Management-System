package com.hamza.student_management_system.course.facade;

import com.hamza.student_management_system.course.datamodels.CourseDto;
import com.hamza.student_management_system.course.facade.interfaces.CourseFacade;
import com.hamza.student_management_system.course.mapper.interfaces.CourseMapper;
import com.hamza.student_management_system.course.services.interfaces.CourseService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
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

    @Override
    public CourseDto registerCourse(Long courseId) {
        return this.courseMapper.mapCourseToCourseDto(this.courseService.registerCourse(courseId));
    }

    @Override
    public CourseDto cancelCourseRegistration(Long courseId) {
        return this.courseMapper.mapCourseToCourseDto(this.courseService.cancelCourseRegistration(courseId));
    }

    @Override
    public void getCourseSchedulePdf(HttpServletResponse response, Long courseId) throws IOException {
        this.courseService.getCourseSchedulePdf(response, courseId);
    }
}
