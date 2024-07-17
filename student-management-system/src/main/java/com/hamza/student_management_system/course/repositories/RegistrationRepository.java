package com.hamza.student_management_system.course.repositories;

import com.hamza.student_management_system.course.entities.Course;
import com.hamza.student_management_system.course.entities.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    @Query("SELECT r.course FROM Registration r WHERE r.user.id = :userId AND r.status = 'REGISTERED'")
    List<Course> findCoursesByUserId(Long userId);
}
