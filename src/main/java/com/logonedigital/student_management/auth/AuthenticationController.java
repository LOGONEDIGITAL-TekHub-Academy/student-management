package com.logonedigital.student_management.auth;

import com.logonedigital.student_management.common.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("auth")

@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegistrationRequest registrationRequest){
        authenticationService.register(registrationRequest);
        return ResponseEntity.ok(new ApiResponse("ok",null));
    }



}
