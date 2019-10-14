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

@Service
public class TwitServiceImpl implements TwitService {

	@Autowired
	private ServiceImplUtil util;
	@Autowired
	private TwitRepository twitRepo;
	@Autowired
	private ResponseRepository responseRepo;

	public Twit getTwit(String id) {
		return util.checkTwitExists(twitRepo, id);
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
		Twit twit = util.checkTwitExists(twitRepo, id);
		Response response = util.checkResponseExists(responseRepo, responseId);
		twit.getResponses().add(response);
		twitRepo.save(twit);
	}

	public void removeResponse(String id, String responseId) {
		Twit twit = util.checkTwitExists(twitRepo, id);
		util.checkResponseExists(responseRepo, responseId);
		if (twit.getResponses().stream().filter(r -> r.getId().equalsIgnoreCase(responseId)) != null) {
			twit.getResponses().removeIf(t -> t.getId().equalsIgnoreCase(responseId));
			twitRepo.save(twit);
		} else {
			throw new EntryNotFoundException(responseId);
		}
	}

	@Override
	public List<Response> getResponsesOnTwit(String id) {
		Twit twit = util.checkTwitExists(twitRepo, id);
		return twit.getResponses();

	}
}