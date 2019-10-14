package com.project.twitapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.twitapi.models.Response;
import com.project.twitapi.services.ResponseService;

@RestController
@RequestMapping("/response")
public class ResponseController {

	@Autowired
	private ResponseService responseService;

	
	@GetMapping("{id}")
	public ResponseEntity<Response> getResponse(@PathVariable String id) {
		Response response = responseService.getResponse(id);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping
	public ResponseEntity<Response> createResponse(@RequestBody Response response) {
		responseService.createResponse(response);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteResponse(@PathVariable String id) {
		responseService.deleteResponse(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}
}