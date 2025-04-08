package com.logonedigital.student_management.auth;


import com.logonedigital.student_management.security.JwtService;
import com.logonedigital.student_management.user.Role;
import com.logonedigital.student_management.user.User;
import com.logonedigital.student_management.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Set;

@Service
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationService(PasswordEncoder passwordEncoder, UserRepository userRepository, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public void register(RegistrationRequest request) {
//
//        //Builder User Info
//        var user = User.builder()
//                .firstname(request.getFirstname())
//                .lastname(request.getLastname())
//                .email(request.getEmail())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .accountLocked(false)
//                .enabled(false)
//                .roles(Set.of(Role.ROLE_ADMIN))
//                .build();


        var user = new User();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAccountLocked(false);
        user.setEnabled(true);
        user.setRoles(Set.of(Role.ROLE_ADMIN));


        //save
        userRepository.save(user);
        //send email for activation account
        //TODO Send Verification Validation Email
    }

    public AuthenticationResponse authenticate(@Valid AuthenticationRequest request) {

        // on va appelle l'auth manager
        // puis authentifier la req qui vient en utilisant le UsernamePasswordAuthenticationToken


        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        // Enregistrer dans le securitycontext
        SecurityContextHolder.getContext().setAuthentication(auth);

        // ajouter les claims
        var claims = new HashMap<String, Object>();
        //getting authenticated user principal
        var user = ((User)auth.getPrincipal());

        claims.put("fullName", user.fullName());
        // Generate le Json Web Token(jwt)

        var jwt = jwtService.generateToken(claims, user);
        AuthenticationResponse response = new AuthenticationResponse();
        response.setToken(jwt);
        return response;

        // pour builder
        //return AuthenticationResponse.builder().token(jwt).build();
    }
}
