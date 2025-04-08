package com.logonedigital.student_management.auth;


import com.logonedigital.student_management.user.Role;
import com.logonedigital.student_management.user.User;
import com.logonedigital.student_management.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthenticationService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void register(RegistrationRequest request) {

        //Builder User Info
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(Set.of(Role.ROLE_ADMIN))
                .build();

        //save
        userRepository.save(user);
        //send email for activation account
        //TODO Send Verification Validation Email
    }
}
