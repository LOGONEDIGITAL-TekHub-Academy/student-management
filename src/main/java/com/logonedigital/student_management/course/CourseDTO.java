package com.logonedigital.student_management.course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CourseDTO {
    private Integer id;

    @NotBlank(message = "The title is required")
    @Size(max = 50, message = "Not above 100 characters")
    private String title;

    @Size(max = 500, message = "Not above 500 characters")
    private String description;

    @NotBlank(message = "Teacher name is required")
    @Size(max = 32, message = "Not above 32")
    private String teacherName;
}
