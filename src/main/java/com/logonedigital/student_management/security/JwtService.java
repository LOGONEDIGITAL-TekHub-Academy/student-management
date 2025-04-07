package com.logonedigital.student_management.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    public String extractUsername(String jwt) {
        //TODO Implement logic to extractUsername from jwt
        return null;
    }

    public boolean isTokenValid(String jwt, UserDetails userDetails) {
        //TODO Implement logic to check if token is valid, for a userDetails
        return false;
    }


}
