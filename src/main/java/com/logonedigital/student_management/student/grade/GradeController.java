package com.logonedigital.student_management.student.grade;

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
@RequestMapping("/grades")
@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;

    // 1. Créer une nouvelle note
    @PostMapping
    public ResponseEntity<ApiResponse> createNote(@RequestBody @Valid GradeDTO gradeDTO) {
        GradeDTO createGrade = gradeService.save(gradeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Saved", createGrade));
    }

}
