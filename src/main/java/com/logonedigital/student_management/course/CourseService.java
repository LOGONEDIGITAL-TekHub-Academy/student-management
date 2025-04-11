package com.logonedigital.student_management.course;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;



    public CourseDTO save(@Valid CourseDTO courseDTO) {
            Course course = courseMapper.toEntity(courseDTO);
            Course saved = courseRepository.save(course);
            return courseMapper.toDTO(saved);
    }
}
