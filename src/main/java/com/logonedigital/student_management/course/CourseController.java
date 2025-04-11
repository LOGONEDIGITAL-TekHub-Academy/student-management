package com.logonedigital.student_management.course;

import com.logonedigital.student_management.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<ApiResponse> save(
            @RequestBody @Valid CourseDTO courseDTO
    )
    {
        CourseDTO createdCourse = courseService.save(courseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Saved successfully", createdCourse));
    }
}
