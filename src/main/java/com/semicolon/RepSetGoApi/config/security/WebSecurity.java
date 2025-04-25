package com.semicolon.RepSetGoApi.config.security;

import com.semicolon.RepSetGoApi.Services.userServices.UserService;
import com.semicolon.RepSetGoApi.Services.userServices.implementations.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurity {
    @Autowired
    UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private Environment environment;
    public WebSecurity(Environment environment){
        this.environment=environment;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http, AuthorizationFilter authorizationFilter) throws Exception {
        // Configure AuthenticationManager
        AuthenticationManager authenticationManager = authenticationManager(http);
        
        // Create and configure AuthenticationFilter
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager, userService, environment);
        authenticationFilter.setFilterProcessesUrl("/rep-set-go/users/login");
        
        // Configure HTTP Security
        http
            .csrf().disable()
            .cors().and()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/rep-set-go/users").permitAll()
                .requestMatchers("/rep-set-go/users/login").permitAll()
                .requestMatchers(HttpMethod.GET,"/rep-set-go/status").permitAll()
                .requestMatchers("/rep-set-go/**").authenticated()
                .anyRequest().authenticated()
            )
            .addFilter(authenticationFilter)
            .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
            .authenticationManager(authenticationManager)
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

    @Bean
    public AuthorizationFilter authorizationFilter(Environment env) {
        return new AuthorizationFilter(env);
    }
}
