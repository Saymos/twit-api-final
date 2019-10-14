package com.project.twitapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class UserServiceErrorAdvice {
	
	
    private ResponseEntity<String> error(HttpStatus status, Exception e) {
        return ResponseEntity.status(status).body(e.getMessage());
    }
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({EntryNotFoundException.class})
    public void handle(EntryNotFoundException e) {
    }


}
