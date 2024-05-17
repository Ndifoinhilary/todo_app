package com.hilaryd.springboottodoapp.config;

import com.hilaryd.springboottodoapp.security.JwtAuthenticationEntryPoint;
import com.hilaryd.springboottodoapp.security.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.swing.undo.AbstractUndoableEdit;

@Configuration
@EnableMethodSecurity
@AllArgsConstructor
public class SpringSecurityConfig {
    private JwtAuthenticationFilter authenticationFilter;
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    private UserDetailsService userDetailsService;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorize -> {
//                            Role base security
//                            authorize.requestMatchers(HttpMethod.POST, "/api/**" ).hasRole("ADMIN");
//                            authorize.requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN");
//                            authorize.requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN");
//                            authorize.requestMatchers(HttpMethod.GET, "/api/**").permitAll();
//                            authorize.requestMatchers(HttpMethod.PATCH,"/api/**").hasAnyRole("ADMIN", "USER");
                            authorize.requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll();
                                authorize.anyRequest().authenticated(); }
                 ).httpBasic(Customizer.withDefaults());
        httpSecurity.exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint));
        httpSecurity.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
//setting up the authentication manager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

//    How to configure inmemory authenticaiton
//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails admin = User.builder().username("Admin")
//                .password(passwordEncoder().encode("codeadmin"))
//                .roles("USER")
//                .build();
//        UserDetails code = User.builder().username("cody")
//                .password(passwordEncoder().encode("code123"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, code);
//    }
}
