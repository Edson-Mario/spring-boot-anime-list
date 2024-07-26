package com.springboot.animelist.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class ValidationExceptionDetails extends ExceptionDetails{
    private String fields;
    private String fieldsMessage;
}
