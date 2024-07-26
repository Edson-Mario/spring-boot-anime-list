package com.springboot.animelist.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnimePatchNameRequestBody {
    private Long id;
    @NotEmpty(message = "Anime name cannot be empty")
    private String name;
}
