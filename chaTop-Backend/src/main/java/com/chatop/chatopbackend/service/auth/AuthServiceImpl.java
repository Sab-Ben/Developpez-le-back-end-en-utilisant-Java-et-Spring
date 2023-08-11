package com.chatop.chatopbackend.service.auth;

import com.chatop.chatopbackend.dto.request.LoginDto;
import com.chatop.chatopbackend.dto.request.RegisterUserDto;
import com.chatop.chatopbackend.dto.response.JwtResponse;
import com.chatop.chatopbackend.dto.response.UserResponseDto;
import com.chatop.chatopbackend.entity.User;
import com.chatop.chatopbackend.repository.UserRepository;
import com.chatop.chatopbackend.service.JwtProvider;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ModelMapper modelMapper;

    private final JwtProvider jwtProvider;

    public AuthServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ModelMapper modelMapper, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
        this.jwtProvider = jwtProvider;
    }

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
