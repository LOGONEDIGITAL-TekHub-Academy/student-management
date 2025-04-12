package com.logonedigital.student_management.student;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;


@Builder
public record StudentDTO(
        @NotEmpty(message = "Firstname is mandatory")
        @NotBlank(message = "Firstname is mandatory")
        String firstname,
        String lastname,
        @NotEmpty(message = "Phone is mandatory")
        @NotBlank(message = "Phone is mandatory")
        String phone,
        @NotEmpty(message = "Matricule is mandatory")
        @NotBlank(message = "Matricule is mandatory")
        String matricule,
        @NotEmpty(message = "Email is mandatory")
        @NotBlank(message = "Email is mandatory")
        @Email(message = "Email not well formatted")
        String email
) { }
