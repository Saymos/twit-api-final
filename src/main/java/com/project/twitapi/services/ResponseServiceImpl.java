package com.project.twitapi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.twitapi.exceptions.DuplicateEntryException;
import com.project.twitapi.exceptions.EntryNotFoundException;
import com.project.twitapi.models.Response;
import com.project.twitapi.models.User;
import com.project.twitapi.repositories.ResponseRepository;
import com.project.twitapi.repositories.UserRepository;

@Service
public class ResponseServiceImpl implements ResponseService {

	@Autowired
	private ServiceImplUtil util;
	@Autowired
	private ResponseRepository responseRepo;
	@Autowired
	private UserRepository userRepo;

	public Response getResponse(String id) {
		return util.getRepsonseIfResponseExists(responseRepo, id);
	}

	public Response createResponse(Response response) {
		if (response.getId() != null) {
			Optional<Response> responseInDb = responseRepo.findById(response.getId());
			if (responseInDb.isPresent()) {
				throw new DuplicateEntryException(response.getId());
			}
		}
		Optional<User> user = userRepo.findById(response.getUserId());
		user.orElseThrow(() -> new EntryNotFoundException(response.getUserId()));
		responseRepo.save(response);
		return response;
	}

	public void deleteResponse(String id) {
		Optional<Response> response = responseRepo.findById(id);
		response.orElseThrow(() -> new EntryNotFoundException(id));
		responseRepo.deleteById(id);
	}

}
