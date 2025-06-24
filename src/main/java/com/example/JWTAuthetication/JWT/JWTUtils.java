package com.example.JWTAuthetication.JWT;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Component
public class JWTUtils {
    private static final Logger logger =  LoggerFactory.getLogger(JWTUtils.class);

    // secret And Expiration in miliseconds fetched from application.properties
        @Value("${spring.app.jwtSecret}")
        private String jwtSecret;
        @Value("${spring.app.jwtExpirationMs}")
        private long  jwtExpirationMs;

    // Generates key from BASE64 String
        private Key key(){
            return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
        }

    //extracts JWt from "Authorization" header
        public String getJwtFromHeader(HttpServletRequest request){
            String bearerToken = request.getHeader("Authorization");
            if(bearerToken!=null && bearerToken.startsWith("Bearer ")){
                return bearerToken.substring(7);
            }
            return  null;

        }

    // generates token from subject(username)  with expiration time
        public String generateTokenFromUsername(UserDetails userDetails){
            String username = userDetails.getUsername();
            return Jwts.builder().subject(username).issuedAt(new Date())
                    .expiration(new Date((new Date()).getTime()+jwtExpirationMs))
                    .signWith(key()).compact();

        }

    // extracts  subject(username) from jwt token payload
        public String getUserNameFromJwtToken(String token){
            return Jwts.parser()
                    .verifyWith((SecretKey) key())
                            .build().parseSignedClaims(token)
                    .getPayload().getSubject();
        }

    // validates JWTtoken using sec
        public boolean validateJwtToken(String authToken) {
            try {
                System.out.println("Validate");
                Jwts.parser().verifyWith((SecretKey) key())
                        .build().parseSignedClaims(authToken);
                return true;
            } catch (MalformedJwtException e) {
                logger.error("Invalid JWT token (malformed): {}", e.getMessage());
            } catch (ExpiredJwtException e) {
                logger.error("JWT token is expired: {}", e.getMessage());
            } catch (UnsupportedJwtException e) {
                logger.error("JWT token is unsupported: {}", e.getMessage());
            } catch (IllegalArgumentException e) {
                logger.error("JWT claims string is empty: {}", e.getMessage());
            }
            return false;
        }




}
