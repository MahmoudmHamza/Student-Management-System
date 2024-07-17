package com.hamza.student_management_system.course.services;

import com.hamza.student_management_system.course.entities.Course;
import com.hamza.student_management_system.course.repositories.CourseRepository;
import com.hamza.student_management_system.course.repositories.RegistrationRepository;
import com.hamza.student_management_system.course.services.interfaces.CourseService;
import com.hamza.student_management_system.user.entities.User;
import com.hamza.student_management_system.user.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final RegistrationRepository registrationRepository;
    private final UserRepository userRepository;

    @Override
    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public List<Course> findCoursesByUserId() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("Can't find user with name: " + userDetails.getUsername()));

        return registrationRepository.findCoursesByUserId(user.getId());
    }

    @Override
    public Course findCourseById(Long id) {
        return this.courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Couldn't find user courses for user of id: " + id));
    }

    //TODO: implement later
    @Override
    public Course addNewCourse(Long userId, Course newProduct) {
        return null;
    }

    //TODO: implement later
    @Override
    public Course removeCourse(Long userId, Long id) {
        return null;
    }
}
