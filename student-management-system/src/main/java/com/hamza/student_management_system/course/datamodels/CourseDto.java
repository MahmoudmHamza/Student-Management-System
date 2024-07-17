package com.hamza.student_management_system.course.datamodels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {
    private String courseCode;
    private String courseName;
    private String description;
    private int credits;
}
