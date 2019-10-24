package com.project.twitapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.twitapi.models.Response;
import com.project.twitapi.models.Twit;
import com.project.twitapi.services.TwitService;

@RestController
@RequestMapping("/twit")
public class TwitController {

	@Autowired
	private TwitService twitService;

	@GetMapping("{id}")
	public ResponseEntity<Twit> getTwit(@PathVariable String id) {
		Twit twit = twitService.getTwit(id);
		return ResponseEntity.status(HttpStatus.OK).body(twit);
	}

	@PostMapping
	public ResponseEntity<Twit> createTwit(@RequestBody Twit twit) {
		twitService.createTwit(twit);
		return ResponseEntity.status(HttpStatus.CREATED).body(twit);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteTwit(@PathVariable String id) {
		twitService.deleteTwit(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}

	@PutMapping("addResponse/{id}")
	public ResponseEntity<?> addResponse(@PathVariable String id, @RequestBody String responseId) {
		twitService.addResponse(id, responseId);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@PutMapping("removeResponse/{id}")
	public ResponseEntity<?> removeResponse(@PathVariable String id, @RequestBody String responseId) {
		twitService.removeResponse(id, responseId);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	@GetMapping("getResponsesOnTwit/{id}")
	public ResponseEntity<List<Response>> getResponsesOnTwit(@PathVariable String id) {
		List<Response> list = twitService.getResponsesOnTwit(id);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	@PutMapping("addLike/{id}")
	public ResponseEntity<?> addLike(@PathVariable String id, @RequestBody String userId) {
		twitService.addLike(id, userId);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
}