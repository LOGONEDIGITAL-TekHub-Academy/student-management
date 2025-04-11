package com.logonedigital.student_management.student.grade;

import lombok.Data;

@Data
public class GradeDTO {
    private Integer id;
    private Double value;
    private String appreciation;
    private Integer studentId;
    private Integer courseId;
}
