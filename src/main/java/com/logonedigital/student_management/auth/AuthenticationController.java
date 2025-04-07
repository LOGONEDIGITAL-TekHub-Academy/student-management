package com.logonedigital.student_management.auth;

import com.logonedigital.student_management.common.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/auth")
@RestController
public class AuthenticationController {

    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegistrationRequest request) {
        service.register(request);
        return ResponseEntity.ok(new ApiResponse("Successfully registered user", null));
    }

}
