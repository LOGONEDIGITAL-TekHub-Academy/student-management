package com.logonedigital.student_management.student.report;

import com.itextpdf.text.DocumentException;
import com.logonedigital.student_management.common.dto.ApiResponse;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reports")
public class ReportController {


    private final ReportService reportService;

    @PostMapping("/{studentId}/send")
    public ResponseEntity<ApiResponse> sendReport(
            @PathVariable Integer studentId
    ) throws IOException, DocumentException, MessagingException {
            String filePath = reportService.generateAndSendReport(studentId);
        return ResponseEntity.ok(new ApiResponse("Report send successfully to", filePath));
    }
}
