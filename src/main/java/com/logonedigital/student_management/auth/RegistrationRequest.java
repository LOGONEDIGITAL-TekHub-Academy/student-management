package com.logonedigital.student_management.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegistrationRequest {

    @NotEmpty(message = "Not empty")
    @NotBlank(message = "Can't be empty")
    private String firstname;

    @NotEmpty(message = "Not empty")
    private String lastname;

    public void setFirstname(@NotEmpty(message = "Not empty") @NotBlank(message = "Can't be empty") String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(@NotEmpty(message = "Not empty") String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(@NotEmpty(message = "Not empty") @NotBlank(message = "Can't be empty") @Email(message = "email invalid") String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotEmpty(message = "Not empty")
    @NotBlank(message = "Can't be empty")
    @Email(message = "email invalid")
    private String email;

    private String password;

    public @NotEmpty(message = "Not empty") @NotBlank(message = "Can't be empty") String getFirstname() {
        return firstname;
    }

    public @NotEmpty(message = "Not empty") String getLastname() {
        return lastname;
    }

    public @NotEmpty(message = "Not empty") @NotBlank(message = "Can't be empty") @Email(message = "email invalid") String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
