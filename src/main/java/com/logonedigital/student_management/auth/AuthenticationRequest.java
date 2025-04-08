package com.logonedigital.student_management.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class AuthenticationRequest {

    @NotEmpty(message = "Email is mandatory")
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email not well formatted")
    private String email;

    @NotEmpty(message = "Password is mandatory")
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password should be 8 characters minimum")
    private String password;

    public @NotEmpty(message = "Email is mandatory") @NotBlank(message = "Email is mandatory") @Email(message = "Email not well formatted") String getEmail() {
        return email;
    }

    public void setEmail(@NotEmpty(message = "Email is mandatory") @NotBlank(message = "Email is mandatory") @Email(message = "Email not well formatted") String email) {
        this.email = email;
    }

    public @NotEmpty(message = "Password is mandatory") @NotBlank(message = "Password is mandatory") @Size(min = 8, message = "Password should be 8 characters minimum") String getPassword() {
        return password;
    }

    public void setPassword(@NotEmpty(message = "Password is mandatory") @NotBlank(message = "Password is mandatory") @Size(min = 8, message = "Password should be 8 characters minimum") String password) {
        this.password = password;
    }
}
