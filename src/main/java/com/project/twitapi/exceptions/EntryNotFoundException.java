package com.project.twitapi.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntryNotFoundException extends RuntimeException {
    public EntryNotFoundException(String message) {
        super(message);
    }
}