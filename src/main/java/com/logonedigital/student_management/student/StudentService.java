package com.logonedigital.student_management.student;

import com.logonedigital.student_management.common.dto.PageResponse;
import com.logonedigital.student_management.user.Role;
import com.logonedigital.student_management.user.User;
import com.logonedigital.student_management.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public PageResponse<StudentDTO> findAllExistingStudents(int page, int size) {
        //Pagination
        Pageable pageable = PageRequest.of(page, size);
        /*Page<Student> existingStudents = studentRepository.findExistingStudents(pageable);*/
        Page<Student> existingStudents = studentRepository.findAll(StudentSpecification. withIsDeleted(false),pageable);

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
        student.setDeleted(false);
        Student savedStudent  = studentRepository.save(student);
        var user = User.builder()
                .firstname(savedStudent.getFirstname())
                .lastname(savedStudent.getLastname())
                .email(savedStudent.getEmail())
                .password(passwordEncoder.encode(savedStudent.getMatricule() +
                        "CFPLTA" +
                        LocalDateTime.now().getYear()))
                .accountLocked(false)
                .enabled(true)
                .roles(Set.of(Role.ROLE_STUDENT))
                .build();
        userRepository.save(user);
        return studentMapper.toDTO(savedStudent);
    }

    public void delete(Integer studentId) {
        studentRepository.findById(studentId).ifPresentOrElse(student -> student.setDeleted(true), () -> {
            throw new EntityNotFoundException("Student not found!");
        });
    }
}
