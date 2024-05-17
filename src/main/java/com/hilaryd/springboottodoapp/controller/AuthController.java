package com.hilaryd.springboottodoapp.controller;

import com.hilaryd.springboottodoapp.dto.JwtAuthResponse;
import com.hilaryd.springboottodoapp.dto.LoginDto;
import com.hilaryd.springboottodoapp.dto.RegisterDto;
import com.hilaryd.springboottodoapp.services.AuthServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor

public class AuthController {
    private AuthServices authServices;




    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String responsse = authServices.register(registerDto);
        return new ResponseEntity<>(responsse, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authServices.login(loginDto);

        var jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }
}
