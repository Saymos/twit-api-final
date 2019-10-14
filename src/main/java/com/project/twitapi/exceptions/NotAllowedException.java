package com.project.twitapi.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class NotAllowedException extends RuntimeException {
    public NotAllowedException(String message) {
        super(message);
    }
}