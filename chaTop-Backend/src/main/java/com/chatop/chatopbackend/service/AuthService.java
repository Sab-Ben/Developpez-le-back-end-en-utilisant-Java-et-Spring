package com.chatop.chatopbackend.service;

import com.chatop.chatopbackend.dto.request.LoginDto;
import com.chatop.chatopbackend.dto.request.RegisterUserDto;
import com.chatop.chatopbackend.dto.response.JwtResponse;
import com.chatop.chatopbackend.dto.response.UserResponseDto;
import com.chatop.chatopbackend.entity.User;
import com.chatop.chatopbackend.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtProvider jwtProvider;

    public JwtResponse register(RegisterUserDto userDto){
        String encodedPassword = bCryptPasswordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);

        User user = this.modelMapper.map(userDto, User.class);
        User savedUser = this.userRepository.save(user);

        return this.jwtProvider.provideJwt(savedUser);
    }

    public JwtResponse login(LoginDto loginDto) {
        String email = loginDto.getEmail();
        String rawPassword = loginDto.getPassword();
        User user = this.userRepository.findByEmail(email);

        if (!this.isUserValid(user, rawPassword)) {
            throw new BadCredentialsException("");
        }

        return this.jwtProvider.provideJwt(user);
    }

    public UserResponseDto me(Authentication principal){
        User authorizedUser = (User) principal.getPrincipal();
        long id = authorizedUser.getId();
        User user = this.userRepository.findById(id).orElse(null);
        return this.modelMapper.map(user, UserResponseDto.class);
    }

    private boolean isUserValid(User user, String rawPassword){
        return user != null && bCryptPasswordEncoder.matches(rawPassword, user.getPassword());
    }

}
