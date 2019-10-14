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

import com.project.twitapi.models.Twit;
import com.project.twitapi.models.User;
import com.project.twitapi.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	@GetMapping("{id}")
	public ResponseEntity<User> getUser(@PathVariable String id) {
		User user = userService.getUser(id);
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}

	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user) {
		userService.createUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteUser(@PathVariable String id) {
		userService.deleteUser(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}

	@PutMapping("addFollower/{id}")
	public ResponseEntity<?> addFollower(@PathVariable String id, @RequestBody String followerUserId) {
		userService.addFollower(id, followerUserId);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@PutMapping("removeFollower/{id}")
	public ResponseEntity<?> removeFollower(@PathVariable String id, @RequestBody String followerUserId) {
		userService.removeFollower(id, followerUserId);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@PutMapping("addTwit/{id}")
	public ResponseEntity<?> addTwit(@PathVariable String id, @RequestBody String twitId) {
		userService.addTwit(id, twitId);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@PutMapping("removeTwitFromUser/{id}")
	public ResponseEntity<?> removeTwitFromUser(@PathVariable String id, @RequestBody String twitId) {
		userService.removeTwitFromUser(id, twitId);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@GetMapping("getUserTwits/{id}")
	public ResponseEntity<List<Twit>> getUserTwits(@PathVariable String id) {
		List<Twit> list = userService.getUserTwits(id);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	@GetMapping("getFollowers/{id}")
	public ResponseEntity<List<User>> getFollowers(@PathVariable String id) {
		List<User> list = userService.getFollowers(id);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
}