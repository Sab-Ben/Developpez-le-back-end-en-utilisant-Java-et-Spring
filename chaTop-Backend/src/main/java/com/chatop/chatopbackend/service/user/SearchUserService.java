package com.chatop.chatopbackend.service.user;

import com.chatop.chatopbackend.dto.response.UserResponseDto;

public interface SearchUserService {

    UserResponseDto findOne(final long id);
}
