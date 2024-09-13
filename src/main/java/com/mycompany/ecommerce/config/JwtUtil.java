package com.mycompany.ecommerce.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${projeto.jwtSecret}")
    private String jwtSecret;

    @Value("${projeto.jwtExpirationMs}")
    private int jwtExpirationMs;



    public String generateToken(UserDetails userDetail) {
        return Jwts.builder().subject(userDetail.getUsername())
                .claim("role", userDetail.getAuthorities())
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(getSigninKey(), SignatureAlgorithm.HS512).compact();
    }


    public Key getSigninKey() {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
        return key;
    }

    public String getUsernameToken(String token) {
        return Jwts.parser().setSigningKey(getSigninKey()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(getSigninKey()).build().parseClaimsJws(authToken);
            System.out.println("Token validado.");
            return true;
        }catch(MalformedJwtException e) {
            System.out.println("Token inválido " + e.getMessage());
        }catch(ExpiredJwtException e) {
            System.out.println("Token expirado " + e.getMessage());
        }catch(UnsupportedJwtException e) {
            System.out.println("Token não suportado " + e.getMessage());
        }catch(IllegalArgumentException e) {
            System.out.println("Token Argumento inválido " + e.getMessage());
        }

        return false;
    }

}