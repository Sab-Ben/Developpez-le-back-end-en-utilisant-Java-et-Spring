package com.chatop.chatopbackend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterUserDto {

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String name;

}
