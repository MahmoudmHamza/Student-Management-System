package com.hamza.student_management_system.course.services;

import com.hamza.student_management_system.core.exceptions.CourseRegistrationException;
import com.hamza.student_management_system.core.utility.PdfGenerator;
import com.hamza.student_management_system.course.entities.Course;
import com.hamza.student_management_system.course.entities.Registration;
import com.hamza.student_management_system.course.repositories.CourseRepository;
import com.hamza.student_management_system.course.repositories.RegistrationRepository;
import com.hamza.student_management_system.course.services.interfaces.CourseService;
import com.hamza.student_management_system.user.entities.User;
import com.hamza.student_management_system.user.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final RegistrationRepository registrationRepository;
    private final PdfGenerator pdfGenerator;

    @Override
    @Cacheable(value = "coursesList")
    public List<Course> findAllCourses() {
        return this.courseRepository.findAll();
    }

    @Override
    @Cacheable(value = "userCourses")
    public List<Course> findCoursesByUserId() {
        User user = getAuthenticatedUserDetails();
        return this.registrationRepository.findCoursesByUserId(user.getId());
    }

    @Override
    public Course findCourseById(Long id) {
        return this.courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Couldn't find user courses for user of id: " + id));
    }

    @Override
    public void getCourseSchedulePdf(HttpServletResponse response, Long courseId) throws IOException {
        Course course = this.findCourseById(courseId);
        this.pdfGenerator.generateCourseSchedulePdf(response, course);
    }

    @Override
    @Transactional
    @CacheEvict(value = "userCourses")
    public Course registerCourse(Long courseId) {
        User user = getAuthenticatedUserDetails();
        Course course = this.findCourseById(courseId);

        if (this.isCourseRegistered(course)) {
            throw new CourseRegistrationException("User is already registered to course: " + course.getCourseName());
        }

        Registration courseRegistration = Registration.builder()
                .user(user)
                .course(course)
                .build();

        this.registrationRepository.save(courseRegistration);
        return course;
    }

    @Override
    @Transactional
    @CacheEvict(value = "userCourses")
    public Course cancelCourseRegistration(Long courseId) {
        User user = getAuthenticatedUserDetails();
        Course course = this.findCourseById(courseId);

        if (!this.isCourseRegistered(course)) {
            throw new CourseRegistrationException("User is not registered to course: " + course.getCourseName());
        }

        this.registrationRepository.deleteByUserIdAndCourseId(user.getId(), courseId);
        return course;
    }

    private User getAuthenticatedUserDetails() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("Can't find user with name: " + userDetails.getUsername()));
    }

    //TODO: test this
    private boolean isCourseRegistered(Course course) {
        List<Course> registeredCourses = this.findCoursesByUserId();
        return registeredCourses.contains(course);
    }
}
