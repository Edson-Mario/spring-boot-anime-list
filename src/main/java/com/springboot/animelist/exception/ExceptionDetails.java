package com.springboot.animelist.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
public class ExceptionDetails {
    private String title;
    private int status;
    private String details;
    private String developerMessage;
    private LocalDateTime timestamp;
}
