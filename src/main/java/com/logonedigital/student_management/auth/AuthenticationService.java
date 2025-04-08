package com.logonedigital.student_management.auth;


import com.logonedigital.student_management.email.EmailService;
import com.logonedigital.student_management.email.EmailTemplateName;
import com.logonedigital.student_management.exception.TokenNotValidException;
import com.logonedigital.student_management.security.JwtService;
import com.logonedigital.student_management.user.*;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

@Service
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;

    public AuthenticationService(PasswordEncoder passwordEncoder,
                                 UserRepository userRepository,
                                 AuthenticationManager authenticationManager, JwtService jwtService, TokenRepository tokenRepository, EmailService emailService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
    }

    public void register(RegistrationRequest request) throws MessagingException {

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
        //Appeler le authenticationManager
        //puis authentifier le req en utilisant le UsernamePasswordAuthenticationToken
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        //Enregistrer dans le SecurityContext
        SecurityContextHolder.getContext().setAuthentication(auth);
        //Additional claims
        var claims = new HashMap<String, Object>();
        // getting authenticated user principal
        var user = ((User)auth.getPrincipal());
        System.out.println(user);
        claims.put("fullName", user.fullName());
        //Generate le Json Web Token
        var jwt = jwtService.generateToken(claims,user);

        return AuthenticationResponse.builder().token(jwt).build();
    }

    public void activateAccount(String activationToken) {
        //chercher le token et verify if token is valid
        Token savedToken = tokenRepository.findByToken(activationToken)
                .orElseThrow(()->new TokenNotValidException("Token not found"));
        if(LocalDateTime.now().isAfter(savedToken.getExpiresAt()))
        {
            //TODO send new activationToken via mail
            throw new TokenNotValidException("Activation TOKEN EXPIRED. New Token has been send to the same adresse");
        }
        //activate account
        var user = userRepository.findByEmail(savedToken.getUser().getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("USER NOT FOUND"));
        user.setEnabled(true);
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);

    }
}
