
package com.ecommerce.project.security.jwt;

import com.ecommerce.project.security.servces.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;
    @Value("${spring.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${spring.ecom.app.jwtCookieName}")
    private String jwtCookie;
    private static final Logger logger= LoggerFactory.getLogger(JwtUtils.class);

    public String getJwtFromCookies(HttpServletRequest request){
        Cookie cookie= WebUtils.getCookie(request,jwtCookie);
        if(cookie!=null){
            System.out.println("COOKIE"+cookie.getValue());
            return cookie.getValue();
        }
        else{
            return null;
        }
    }
    public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal){
        String jwt=generateTokenFromUsername(userPrincipal.getUsername());
        ResponseCookie responseCookie=ResponseCookie.from(jwtCookie,jwt)
                .path("/api")
                .maxAge(24*60*60)
                .httpOnly(false)
                .build();
        return responseCookie;
    }

    public String generateTokenFromUsername(String userDetails){
        return Jwts.builder()
                .subject(userDetails)
                .issuedAt(new Date())
                .expiration(new Date((new Date().getTime()+jwtExpirationMs)))
                .signWith(key())
                .compact();
    }
    public String gettingUsernameFromJWTToken(String token){
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build().parseSignedClaims(token)
                .getPayload().getSubject();
    }
    public Key key(){
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        );
    }
    public boolean validateJwtToken(String authToken){
        try {
            System.out.println("Validate");
            Jwts.parser()
                    .verifyWith((SecretKey) key())
                    .build().parseSignedClaims(authToken);
            return true;
        }
        catch (MalformedJwtException e){
            logger.error("Invalid Jwt token :{}",e.getMessage());
        }
        catch (ExpiredJwtException e){
            logger.error("Expired Jwt token:{}",e.getMessage());
        }
        catch (UnsupportedJwtException e){
            logger.error("Jwt Token is Unsupported:{}",e.getMessage());
        }
        catch (IllegalArgumentException e){
            logger.error("Jwt claims String is empty:{}",e.getMessage());
        }
        return false;
    }
}
