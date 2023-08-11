package com.chatop.chatopbackend.service.message;

import com.chatop.chatopbackend.dto.request.CreateMessageDto;
import com.chatop.chatopbackend.dto.response.MessageResponse;
import com.chatop.chatopbackend.entity.Message;
import com.chatop.chatopbackend.repository.MessageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final ModelMapper modelMapper;

    public MessageServiceImpl(MessageRepository messageRepository, ModelMapper modelMapper) {
        this.messageRepository = messageRepository;
        this.modelMapper = modelMapper;
    }

    public MessageResponse create(CreateMessageDto messageDto){
        Message message = this.modelMapper.map(messageDto, Message.class);
        this.messageRepository.save(message);
        return new MessageResponse().setMessage("Message send with success");
    }
}
