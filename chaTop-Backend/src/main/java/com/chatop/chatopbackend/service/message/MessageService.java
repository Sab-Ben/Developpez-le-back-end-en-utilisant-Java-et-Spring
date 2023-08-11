package com.chatop.chatopbackend.service.message;

import com.chatop.chatopbackend.dto.request.CreateMessageDto;
import com.chatop.chatopbackend.dto.response.MessageResponse;

public interface MessageService {

    MessageResponse create(CreateMessageDto messageDto);
}
