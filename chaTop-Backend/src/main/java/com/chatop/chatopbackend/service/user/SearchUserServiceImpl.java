package com.chatop.chatopbackend.service.user;

import com.chatop.chatopbackend.dto.response.UserResponseDto;
import com.chatop.chatopbackend.entity.User;
import com.chatop.chatopbackend.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class SearchUserServiceImpl implements SearchUserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public SearchUserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserResponseDto findOne(final long id){
        User user = this.userRepository.findById(id).orElse(null);
        return this.modelMapper.map(user, UserResponseDto.class);
    }
}
