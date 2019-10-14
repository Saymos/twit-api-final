package com.project.twitapi.services;

import org.springframework.stereotype.Repository;

import com.project.twitapi.models.Response;

@Repository
public interface ResponseService {
	
	public Response getResponse(String id);
	public Response createResponse(Response response);
	public void deleteResponse(String id);

}
