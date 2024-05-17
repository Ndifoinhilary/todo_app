package com.hilaryd.springboottodoapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt.secret}")
    private String jwtSecret;
    @Value("${app.jwt-expiration-milliseconds}")
    private long jwtExpirationDate;


//    generate the token

    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        var current = new Date();

        var expirationDate = new Date(current.getTime() + jwtExpirationDate);

        String token = Jwts.builder()
                .subject(username)
                .issuedAt(current)
                .expiration(expirationDate)
                .signWith(key())
                .compact();

        return  token;
    }


    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

//    get the name from jwt

    public String getUsername(String token){
        Claims claims = Jwts.parser().setSigningKey(key()).build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token){
        Jwts.parser().setSigningKey(key()).build().parse(token);
        return true;
    }
}
