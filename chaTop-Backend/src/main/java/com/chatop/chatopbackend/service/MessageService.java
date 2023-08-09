package com.chatop.chatopbackend.service;

import com.chatop.chatopbackend.dto.request.CreateMessageDto;
import com.chatop.chatopbackend.dto.response.MessageResponse;
import com.chatop.chatopbackend.entity.Message;
import com.chatop.chatopbackend.repository.MessageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ModelMapper modelMapper;

    public MessageResponse create(CreateMessageDto messageDto){
        Message message = this.modelMapper.map(messageDto, Message.class);
        this.messageRepository.save(message);
        return new MessageResponse().setMessage("Message send with success");
    }
}
