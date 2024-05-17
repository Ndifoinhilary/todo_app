package com.hilaryd.springboottodoapp.services.impl;

import com.hilaryd.springboottodoapp.dto.LoginDto;
import com.hilaryd.springboottodoapp.dto.RegisterDto;
import com.hilaryd.springboottodoapp.entity.Role;
import com.hilaryd.springboottodoapp.entity.User;
import com.hilaryd.springboottodoapp.exceptions.TodoAPIException;
import com.hilaryd.springboottodoapp.repository.RoleRepository;
import com.hilaryd.springboottodoapp.repository.UserRepository;
import com.hilaryd.springboottodoapp.security.JwtTokenProvider;
import com.hilaryd.springboottodoapp.services.AuthServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServicesImpl  implements AuthServices {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    @Override
    public String register(RegisterDto registerDto) {
//check if a user with the email exists
        if (userRepository.existsByUsername(registerDto.getUsername())){
            throw new TodoAPIException(HttpStatus.BAD_REQUEST, "User with the username already exists");
        }
//        check if a user with email already exist
        if (userRepository.existsByEmail(registerDto.getEmail())){
            throw new TodoAPIException(HttpStatus.BAD_REQUEST, "User with the email already exists");
        }

        var user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new  HashSet<>();
//get the role name from the roleRepository
        var user_role = roleRepository.findByName("ROLE_USER");
//add the role to the Set as defined in the user entity
        roles.add(user_role);
//add the role to the user
        user.setRoles(roles);
//save the user
        userRepository.save(user);


        return "User registered successfully go ahead and edit your account ";
    }

    @Override
    public String login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),
                loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }
}
