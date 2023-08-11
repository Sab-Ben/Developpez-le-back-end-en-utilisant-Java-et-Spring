package com.chatop.chatopbackend.service.auth;

import com.chatop.chatopbackend.dto.request.LoginDto;
import com.chatop.chatopbackend.dto.request.RegisterUserDto;
import com.chatop.chatopbackend.dto.response.JwtResponse;
import com.chatop.chatopbackend.dto.response.UserResponseDto;
import org.springframework.security.core.Authentication;

public interface AuthService {

    JwtResponse register(RegisterUserDto userDto);

    JwtResponse login(LoginDto loginDto);

    UserResponseDto me(Authentication principal);
}
