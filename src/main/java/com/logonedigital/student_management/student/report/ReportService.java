package com.logonedigital.student_management.student.report;

import com.itextpdf.text.*;
import com.logonedigital.student_management.common.PdfService;
import com.logonedigital.student_management.email.EmailService;
import com.logonedigital.student_management.student.Student;
import com.logonedigital.student_management.student.StudentRepository;
import com.logonedigital.student_management.student.grade.Grade;
import com.logonedigital.student_management.student.grade.GradeRepository;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReportService {

    private final PdfService pdfService;
    private final EmailService emailService;
    private final StudentRepository studentRepository;
    private final GradeRepository gradeRepository;


    public String generateAndSendReport(Integer studentId)
            throws IOException, DocumentException, MessagingException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(()-> new EntityNotFoundException("Student not found"));

        List<Grade> grades = gradeRepository.findByStudentId(studentId);

        String filePath = pdfService.generateReportPdf(student, grades);

        emailService.sendReport(student, filePath);

        return filePath;
    }
}
