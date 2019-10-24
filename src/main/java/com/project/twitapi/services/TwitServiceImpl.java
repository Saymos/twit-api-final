package com.project.twitapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.twitapi.exceptions.DuplicateEntryException;
import com.project.twitapi.exceptions.EntryNotFoundException;
import com.project.twitapi.models.Response;
import com.project.twitapi.models.Twit;
import com.project.twitapi.repositories.ResponseRepository;
import com.project.twitapi.repositories.TwitRepository;
import com.project.twitapi.repositories.UserRepository;

@Service
public class TwitServiceImpl implements TwitService {

	@Autowired
	private ServiceImplUtil util;
	@Autowired
	private TwitRepository twitRepo;
	@Autowired
	private ResponseRepository responseRepo;
	@Autowired
	private UserRepository userRepo;	

	public Twit getTwit(String id) {
		return util.getTwitIfTwitExists(twitRepo, id);
	}

	public Twit createTwit(Twit twit) {
		if (twit.getId() != null) {
			Optional<Twit> twitInDb = twitRepo.findById(twit.getId());
			if (twitInDb.isPresent()) {
				throw new DuplicateEntryException(twit.getId());
			}
		}
		twitRepo.save(twit);
		return twit;
	}

	public void deleteTwit(String id) {
		Optional<Twit> twit = twitRepo.findById(id);
		twit.orElseThrow(() -> new EntryNotFoundException(id));
		twitRepo.deleteById(id);
	}

	public void addResponse(String id, String responseId) {
		Twit twit = util.getTwitIfTwitExists(twitRepo, id);
		Response response = util.getRepsonseIfResponseExists(responseRepo, responseId);
		twit.getResponses().add(response);
		twitRepo.save(twit);
	}

	public void removeResponse(String id, String responseId) {
		Twit twit = util.getTwitIfTwitExists(twitRepo, id);
		util.getRepsonseIfResponseExists(responseRepo, responseId);
		if (twit.getResponses().stream().filter(r -> r.getId().equalsIgnoreCase(responseId)) != null) {
			twit.getResponses().removeIf(t -> t.getId().equalsIgnoreCase(responseId));
			twitRepo.save(twit);
		} else {
			throw new EntryNotFoundException(responseId);
		}
	}

	public List<Response> getResponsesOnTwit(String id) {
		Twit twit = util.getTwitIfTwitExists(twitRepo, id);
		return twit.getResponses();
	}
	
	public void addLike(String id, String userId) {
		Twit twit = util.getTwitIfTwitExists(twitRepo, id);
		util.checkUserExists(userRepo, userId);
		if (!twit.getLikes().contains(userId)) {
			twit.getLikes().add(userId);
			twitRepo.save(twit);
		} else {
			throw new DuplicateEntryException(userId);
		}
	}
}