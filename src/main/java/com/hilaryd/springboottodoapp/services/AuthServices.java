package com.hilaryd.springboottodoapp.services;

import com.hilaryd.springboottodoapp.dto.LoginDto;
import com.hilaryd.springboottodoapp.dto.RegisterDto;

public interface AuthServices {
    String register(RegisterDto registerDto);
    String login(LoginDto loginDto);
}
