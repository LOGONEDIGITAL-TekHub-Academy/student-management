package com.logonedigital.student_management;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@EnableJpaAuditing()
public class StudentManagementApplication {
	private static final Logger logger = LoggerFactory.getLogger(StudentManagementApplication.class);

	@GetMapping("/home")
	public String home() {
		return "Hello Welcome to Student Management System @ LOGONEDIGITAL";
	}
	public static void main(String[] args) {
		try {
			SpringApplication.run(StudentManagementApplication.class, args);
			logger.info("APPLICATION SUCCESSFULLY STARTED");
		} catch (Exception e) {
            logger.error("ERROR WHILE STARTING APPLICATION: {}", e.getMessage(), e);
			throw e;
		}

	}

}
