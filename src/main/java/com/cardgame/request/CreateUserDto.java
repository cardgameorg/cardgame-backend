package com.cardgame.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateUserDto {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
