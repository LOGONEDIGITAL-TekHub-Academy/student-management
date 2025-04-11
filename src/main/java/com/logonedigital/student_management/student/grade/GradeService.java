package com.logonedigital.student_management.student.grade;

import com.logonedigital.student_management.course.Course;
import com.logonedigital.student_management.course.CourseRepository;
import com.logonedigital.student_management.student.Student;
import com.logonedigital.student_management.student.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GradeService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final GradeMapper gradeMapper;
    private final GradeRepository gradeRepository;

    public GradeDTO save(GradeDTO gradeDTO) {
        //Get the student first
        Student student = studentRepository.findById(gradeDTO.getStudentId()).orElseThrow(()-> new EntityNotFoundException("Student not found"));
        //Get the course
        Course course = courseRepository.findById(gradeDTO.getCourseId()).orElseThrow(()-> new EntityNotFoundException("Course not found"));

        Grade grade = gradeMapper.toEntity(gradeDTO);
        grade.setStudent(student);
        grade.setCourse(course);

        Grade saved = gradeRepository.save(grade);
        return gradeMapper.toDTO(saved);
    }
}
