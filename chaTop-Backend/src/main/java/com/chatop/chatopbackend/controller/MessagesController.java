package com.chatop.chatopbackend.controller;

import com.chatop.chatopbackend.dto.request.CreateMessageDto;
import com.chatop.chatopbackend.dto.response.MessageResponse;
import com.chatop.chatopbackend.service.message.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "Messages")
@RequestMapping("messages")
public class MessagesController implements SecurityController {

    private final MessageService messageService;

    public MessagesController(MessageService messageService) {
        this.messageService = messageService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", content = @Content),
            @ApiResponse(responseCode = "400", content = @Content)
    })
    @Operation(summary = "Creates a message")
    @PostMapping("")
    public MessageResponse create(@Valid @RequestBody CreateMessageDto createMessageDto) {
        return this.messageService.create(createMessageDto);
    }
}
