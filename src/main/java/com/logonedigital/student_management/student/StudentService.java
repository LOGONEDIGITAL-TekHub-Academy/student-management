package com.logonedigital.student_management.student;

import com.logonedigital.student_management.common.dto.PageResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public PageResponse<StudentDTO> findAllExistingStudents(int page, int size) {
        //Pagination
        Pageable pageable = PageRequest.of(page, size);
        /*Page<Student> existingStudents = studentRepository.findExistingStudents(pageable);*/
        Page<Student> existingStudents = studentRepository.findAll(StudentSpecification.withIsDeleted(false),pageable);

        List<StudentDTO> studentDTOS = existingStudents.stream()
                .map(studentMapper::toDTO)
                .toList();

        return new PageResponse<>(
                studentDTOS,
                existingStudents.getNumber(),
                existingStudents.getSize(),
                existingStudents.getTotalElements(),
                existingStudents.getTotalPages(),
                existingStudents.isFirst(),
                existingStudents.isLast()
        );
    }

    public StudentDTO save(@Valid StudentDTO request) {
        Student student = studentMapper.toEntity(request);
        Student savedStudent  = studentRepository.save(student);
        return studentMapper.toDTO(savedStudent);
    }

    public void delete(Integer studentId) {
        studentRepository.findById(studentId).ifPresentOrElse(student -> {
            student.setDeleted(true);
        }, () -> {
            throw new EntityNotFoundException("Student not found!");
        });
    }
}
