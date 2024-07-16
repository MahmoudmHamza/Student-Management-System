package com.hamza.student_management_system.repositories;

import com.hamza.student_management_system.entities.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
}
