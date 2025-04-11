package com.logonedigital.student_management.auth;


import com.logonedigital.student_management.email.EmailService;
import com.logonedigital.student_management.email.EmailTemplateName;
import com.logonedigital.student_management.security.JwtService;
import com.logonedigital.student_management.user.*;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Set;

@Service
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;


    public AuthenticationService(PasswordEncoder passwordEncoder, UserRepository userRepository, AuthenticationManager authenticationManager, JwtService jwtService, TokenRepository tokenRepository, EmailService emailService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
    }

    public void register(RegistrationRequest request) throws MessagingException {
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

        this.sendValidationCodeByEmail(user);
    }

    private void sendValidationCodeByEmail(User user) throws MessagingException {
        var activationCode = this.generateAndSaveActivationCode(user);
        //send mail
        emailService.sendValidationEmail(
                user.getEmail(),
                user.fullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                "",
                activationCode,
                "Account activation"
        );
    }

    private String generateAndSaveActivationCode(User user) {
        String activationCode = this.generateActivationCode();
        var token = new Token();
        token.setToken(activationCode);
        token.setUser(user);
        token.setCreatedAt(LocalDateTime.now());
        token.setExpiresAt(LocalDateTime.now().plusMinutes(15));
        this.tokenRepository.save(token);
        return activationCode;
    }

    private String generateActivationCode() {
        String characters = "0123456789";
        StringBuilder activationCode = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < 6; i++) {
            activationCode.append(characters.charAt(secureRandom.nextInt(characters.length())));
        }
        return activationCode.toString();
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

    public void activateAccount(String token) {
    }
}
