package com.chatop.chatopbackend.controller;

import com.chatop.chatopbackend.dto.response.UserResponseDto;
import com.chatop.chatopbackend.service.user.SearchUserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@OpenAPIDefinition(info = @Info(title = "User"))
public class UserController implements SecurityController {

    private final SearchUserService searchUserService;

    public UserController(SearchUserService searchUserService) {
        this.searchUserService = searchUserService;
    }

    @Operation(summary = "Find a user by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", content = @Content)
    })
    @GetMapping(value = "/{id}")
    public UserResponseDto findOne(@Parameter(description = "the id of the user") @PathVariable("id") final long id){
        return this.searchUserService.findOne(id);
    }
}
