package com.logonedigital.student_management.email;

import com.logonedigital.student_management.student.Student;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    public EmailService(JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendValidationEmail(
            String to, String username, EmailTemplateName emailTemplate,
            String confirmationUrl, String activationCode, String subject
    ) throws MessagingException {

        String templateName;
        if (emailTemplate == null) {
            templateName = "confirm-email";
        }else {
            templateName = emailTemplate.getName();
        }

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                message,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name()
        );

        Map<String, Object> properties = new HashMap<>();
        properties.put("username", username);
        properties.put("confirmationUrl", confirmationUrl);
        properties.put("activationCode", activationCode);

        Context context =  new Context();
        context.setVariables(properties);

        helper.setFrom("tpkdmta@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);

        String template = templateEngine.process(templateName, context);
        helper.setText(template, true);

        mailSender.send(message);
    }

    public void sendReport(Student student, String filePath) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(student.getEmail());
        helper.setSubject("Votre Bulletin de Notes");
        helper.setText("Cher " + student.fullName() + ",\n\nVeuillez trouver ci-joint votre bulletin de notes.");

        FileSystemResource file = new FileSystemResource(new File(filePath));
        helper.addAttachment("Bulletin_" + student.getMatricule() + ".pdf", file);

        mailSender.send(message);
    }

    public void sendReportWithTemplate(
            Student student, String filePath,
            EmailTemplateName emailTemplate
    ) throws MessagingException {

        String templateName;
        if (emailTemplate == null) {
            templateName = "report";
        }else {
            templateName = emailTemplate.getName();
        }

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                message,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name()
        );

        Map<String, Object> properties = new HashMap<>();
        properties.put("month", LocalDateTime.now().getMonth().toString());
        properties.put("year", LocalDateTime.now().getYear());
        properties.put("recipientName", student.fullName());
        properties.put("contactPerson", "Le Responsable");
        properties.put("contactEmail", "lta-contact@gmail.com");
        properties.put("schoolName", "LOGONEDIGITAL TEKHUB ACADEMY");
        properties.put("reportName", "Bulletin_" + student.getMatricule() + ".pdf");

        Context context =  new Context();
        context.setVariables(properties);

        helper.setTo(student.getEmail());
        helper.setSubject("Votre Bulletin de Notes");

        String template = templateEngine.process(templateName, context);
        helper.setText(template, true);

        FileSystemResource file = new FileSystemResource(new File(filePath));
        helper.addAttachment("Bulletin_" + student.getMatricule() + ".pdf", file);

        mailSender.send(message);
    }
}
