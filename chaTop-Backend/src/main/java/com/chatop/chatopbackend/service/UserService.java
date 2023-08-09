package com.chatop.chatopbackend.service;

import com.chatop.chatopbackend.dto.response.UserResponseDto;
import com.chatop.chatopbackend.entity.User;
import com.chatop.chatopbackend.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UserResponseDto findOne(final long id){
        User user = this.userRepository.findById(id).orElse(null);
        return this.modelMapper.map(user, UserResponseDto.class);
    }
}
