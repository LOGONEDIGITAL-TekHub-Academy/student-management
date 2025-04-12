package com.logonedigital.student_management.student.grade;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class GradeDTO {
    private Integer id;
    @Positive(message = "Grade value should be positive")
    private Double value;
    @NotBlank(message = "Appreciation is mandatory")
    @NotEmpty(message = "Appreciation is mandatory")
    private String appreciation;
    private Integer studentId;
    private Integer courseId;
}
