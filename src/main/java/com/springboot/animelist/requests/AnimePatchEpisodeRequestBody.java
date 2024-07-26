package com.springboot.animelist.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnimePatchEpisodeRequestBody {
    private Long id;
    @NotNull(message = "Anime episode cannot be null")
    private Short episode;
}
