package com.semicolon.RepSetGoApi.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.semicolon.RepSetGoApi.Services.userServices.UserService;
import com.semicolon.RepSetGoApi.users.LoginUserRequestDo;
import com.semicolon.RepSetGoApi.users.UserDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private UserService userService;


    private Environment environment;


    public AuthenticationFilter(AuthenticationManager authenticationManager, UserService userService,Environment environment) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.environment=environment;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginUserRequestDo creds = new ObjectMapper()
                    .readValue(request.getInputStream(), LoginUserRequestDo.class);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPwd_hash(),
                            new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
        //the auth gives the user details
        String userName = ((User) auth.getPrincipal()).getUsername();
        UserDTO userDto = userService.getUserDetailsByEmail(userName);
        //jwt token generation
        String token = environment.getProperty("token.secret_key");
        byte[] token_bytes = Base64.getEncoder().encode(token.getBytes());
        SecretKey secretKey = Keys.hmacShaKeyFor(token_bytes);

        Instant now = Instant.now();

        //generating token
        String jwtToken = Jwts.builder()
                .subject(userDto.getUserId())
                .expiration(Date.from(now.
                        plusMillis(Long.parseLong(environment.
                                getProperty("token.expiration_milli_seconds")))))
                .issuedAt(Date.from(now))
                .signWith(secretKey)
                .compact();

        res.addHeader("userId", userDto.getUserId());
        res.addHeader("token",jwtToken);
    }
}
