package com.project.twitapi.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.twitapi.exceptions.EntryNotFoundException;
import com.project.twitapi.models.Response;
import com.project.twitapi.models.Twit;
import com.project.twitapi.models.User;
import com.project.twitapi.repositories.ResponseRepository;
import com.project.twitapi.repositories.TwitRepository;
import com.project.twitapi.repositories.UserRepository;

@Service
public class ServiceImplUtil {
	
	public User getUserIfUserExists(UserRepository userRepo, String id) {
		Optional<User> optUser = userRepo.findById(id);
		optUser.orElseThrow(() -> new EntryNotFoundException("User with Id: " +id));
		return optUser.get();
	}
	
	public Twit getTwitIfTwitExists(TwitRepository twitRepo, String id) {
		Optional<Twit> optTwit = twitRepo.findById(id);
		optTwit.orElseThrow(() -> new EntryNotFoundException("Twit with Id: " + id));
		return optTwit.get();
		 
	}
	
	public Response getRepsonseIfResponseExists(ResponseRepository responseRepo, String id) {
		Optional<Response> optResponse = responseRepo.findById(id);
		optResponse.orElseThrow(() -> new EntryNotFoundException("Response with Id: " + id));
		return optResponse.get();
	}

	public boolean checkTwitExists(TwitRepository twitRepo, String id) {
		Optional<Twit> optTwit = twitRepo.findById(id);
		return optTwit.isPresent();
	}
	
	public boolean checkUserExists(UserRepository userRepo, String id) {
		Optional<User> optUser = userRepo.findById(id);
		return optUser.isPresent();
	}
	
	public boolean checkResponseExists(ResponseRepository ResponseRepo, String id) {
		Optional<Response> optResponse = ResponseRepo.findById(id);
		return optResponse.isPresent();
	}
}
