package com.logonedigital.student_management.student.grade;

import org.springframework.stereotype.Component;

@Component
public class GradeMapper {
    public GradeDTO toDTO(Grade grade) {
        GradeDTO dto = new GradeDTO();
        dto.setValue(grade.getValue());
        dto.setStudentId(grade.getStudent().getId());
        dto.setCourseId(grade.getCourse().getId());
        dto.setAppreciation(grade.getAppreciation());
        return dto;
    }

    public Grade toEntity(GradeDTO dto) {
        Grade grade = new Grade();
        grade.setId(dto.getId());
        grade.setValue(dto.getValue());
        grade.setAppreciation(dto.getAppreciation());
        return grade;
    }
}
