package com.logonedigital.student_management.auth;


import com.logonedigital.student_management.user.Role;
import com.logonedigital.student_management.user.User;
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


    public void register(RegistrationRequest registrationRequest) {

        //Builder user info

       /* var user = User.builder()
                .firstname(registrationRequest.getFirstname())
                .lastname(registrationRequest.getLastname())
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(Set.of(Role.ROLE_ADMIN))//TODO
                .build();*/

        var user = new User();
        user.setFirstname(registrationRequest.getFirstname());
        user.setLastname(registrationRequest.getLastname());
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setAccountLocked(false);
        user.setEnabled(false);
        user.setRoles(Set.of(Role.ROLE_ADMIN));

        //save
        userRepository.save(user);

        //TODO send email verif
        // send email for activation account

    }
}
