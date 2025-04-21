package com.logonedigital.student_management.student;

import com.logonedigital.student_management.common.dto.ApiResponse;
import com.logonedigital.student_management.common.dto.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse> save(
            @RequestBody @Valid StudentDTO request
    ) {
        //TODO Exception when ROLE_STUDENT Try to add Other Student
        var studentDto = studentService.save(request);
        return ResponseEntity.ok(new ApiResponse("Saved successfully", studentDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<PageResponse<StudentDTO>> findAll(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    )
    {
        return ResponseEntity.ok(studentService.findAllExistingStudents(page,size));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{studentId}")
    public ResponseEntity<ApiResponse> delete(
        @PathVariable Integer studentId
    ){
        studentService.delete(studentId);
        return ResponseEntity.ok(new ApiResponse("Deleted successfully", null));
    }
}
