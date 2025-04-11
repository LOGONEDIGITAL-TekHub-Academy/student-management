package com.logonedigital.student_management.student;

import org.springframework.stereotype.Service;

@Service
public class StudentMapper {
    public StudentDTO toDTO(Student student) {

        return StudentDTO.builder()
                .firstname(student.getFirstname())
                .lastname(student.getLastname())
                .email(student.getEmail())
                .phone(student.getPhone())
                .matricule(student.getMatricule())
                .build();
    }

    public Student toEntity(StudentDTO dto) {

        return Student.builder()
                .firstname(dto.firstname())
                .lastname(dto.lastname())
                .email(dto.email())
                .phone(dto.phone())
                .matricule(dto.matricule())
                .build();
    }
}
