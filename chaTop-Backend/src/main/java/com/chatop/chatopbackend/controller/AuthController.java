package com.chatop.chatopbackend.controller;

import com.chatop.chatopbackend.dto.request.LoginDto;
import com.chatop.chatopbackend.dto.request.RegisterUserDto;
import com.chatop.chatopbackend.dto.response.JwtResponse;
import com.chatop.chatopbackend.dto.response.UserResponseDto;
import com.chatop.chatopbackend.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/me")
    @Operation(summary = "Gets the user information", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", content = @Content)
    }
    )
    public UserResponseDto me(Authentication principal){
        return this.authService.me(principal);
    }

    @Operation(summary = "Creates a new user")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content)
    })
    @PostMapping("/register")
    public JwtResponse register(@Valid @RequestBody RegisterUserDto registerUserDto){
        return this.authService.register(registerUserDto);
    }

    @Operation(summary = "Authenticates the user")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", content = @Content)
    })
    @PostMapping("/login")
    public JwtResponse login(@Valid @RequestBody LoginDto loginDto){
        return this.authService.login(loginDto);
    }
}
