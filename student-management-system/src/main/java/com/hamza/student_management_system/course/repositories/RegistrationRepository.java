package com.hamza.student_management_system.course.repositories;

import com.hamza.student_management_system.course.entities.Course;
import com.hamza.student_management_system.course.entities.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    @Query("SELECT r.course FROM Registration r WHERE r.user.id = :userId AND r.status = 'REGISTERED'")
    List<Course> findCoursesByUserId(Long userId);

    Optional<Registration> findByUserId(Long userId);

    @Query("UPDATE Registration r SET r.status = 'CANCELLED' WHERE r.user.id = :userId AND r.course.id = :courseId AND r.status = 'REGISTERED'")
    void updateCourseRegistrationStatus(Long userId, Long courseId);
}
