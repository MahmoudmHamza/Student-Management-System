package com.hamza.student_management_system.course.services;

import com.hamza.student_management_system.core.exceptions.CourseRegistrationException;
import com.hamza.student_management_system.core.utility.PdfGenerator;
import com.hamza.student_management_system.course.entities.Course;
import com.hamza.student_management_system.course.entities.Registration;
import com.hamza.student_management_system.course.repositories.CourseRepository;
import com.hamza.student_management_system.course.repositories.RegistrationRepository;
import com.hamza.student_management_system.course.services.interfaces.CourseService;
import com.hamza.student_management_system.user.entities.User;
import com.hamza.student_management_system.user.services.interfaces.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserService userService;
    private final RegistrationRepository registrationRepository;

    private final PdfGenerator pdfGenerator;

    @Override
    @Cacheable(value = "coursesList")
    public List<Course> findAllCourses() {
        log.info("Fetching all available courses");
        return this.courseRepository.findAll();
    }

    @Override
    @CachePut(value = "userCourses")
    public List<Course> findCoursesByUserId() {
        User user = this.getAuthenticatedUserDetails();
        log.info(String.format("Fetching registered course for user with id: %s", user.getId()));
        return this.registrationRepository.findCoursesByUserId(user.getId());
    }

    @Override
    @Cacheable(value = "courseDetails", key = "#id")
    public Course findCourseById(Long id) {
        return this.courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Couldn't find course of id: %s", id)));
    }

    @Override
    public void getCourseSchedulePdf(HttpServletResponse response, Long courseId) throws IOException {
        log.info(String.format("Requesting schedule for course with id: %s", courseId));
        Course course = this.findCourseById(courseId);
        this.pdfGenerator.generateCourseSchedulePdf(response, course);
    }

    @Override
    @Transactional
    @CachePut(value = "userCourses")
    public Course registerCourse(Long courseId) {
        User user = getAuthenticatedUserDetails();
        Course course = this.findCourseById(courseId);

        log.info(String.format("Requesting student with id %s registration for course with id %s", user.getId(), courseId));

        if (this.isCourseRegistered(course)) {
            log.error(String.format("Student with id %s is already registered to course with id %s", user.getId(), courseId));
            throw new CourseRegistrationException(String.format("User is already registered to course: %s", course.getCourseName()));
        }

        Registration courseRegistration = Registration.builder()
                .user(user)
                .course(course)
                .registrationDate(LocalDateTime.now())
                .version(0)
                .build();

        this.registrationRepository.save(courseRegistration);
        return course;
    }

    @Override
    @Transactional
    @CachePut(value = "userCourses")
    public void cancelCourseRegistration(Long courseId) {
        User user = getAuthenticatedUserDetails();
        Course course = this.findCourseById(courseId);

        log.info(String.format("Requesting student with id %d cancel registration for course with id %d", user.getId(), courseId));

        if (!this.isCourseRegistered(course)) {
            log.error(String.format("Student with id %d is not registered to course with id %d", user.getId(), courseId));
            throw new CourseRegistrationException(String.format("User is not registered to course: %s", course.getCourseName()));
        }

        this.registrationRepository.cancelStudentRegistration(user.getId(), courseId);
    }

    private User getAuthenticatedUserDetails() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.findUserByName(userDetails.getUsername());
    }

    private boolean isCourseRegistered(Course course) {
        List<Course> registeredCourses = this.findCoursesByUserId();
        return registeredCourses.contains(course);
    }
}
