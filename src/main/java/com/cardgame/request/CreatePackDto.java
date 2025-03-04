package com.cardgame.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

@Data
public class CreatePackDto {
    @NotNull
    private String title;
    @NotNull
    private String descTitle;
}
