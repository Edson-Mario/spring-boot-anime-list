package com.springboot.animelist.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnimePutRequestBody {
    private Long id;
    @NotEmpty(message = "Anime cannot be empty")
    private String name;
    @NotNull(message = "Anime episode cannot be null")
    private Short episode;
}
