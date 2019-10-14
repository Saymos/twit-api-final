package com.project.twitapi.services;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.project.twitapi.models.Response;
import com.project.twitapi.models.Twit;

@Repository
public interface TwitService {

	public Twit getTwit(String id);
	public Twit createTwit(Twit twit);
	public void deleteTwit(String id);
	public void addResponse(String id, String responseId);
	public void removeResponse(String id, String responseId);
	public List<Response> getResponsesOnTwit(String id);
	
}
