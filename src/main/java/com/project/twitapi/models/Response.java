package com.project.twitapi.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document()
public class Response {
	
	@Id
	private String id;
	
	private String userId;
	private String response;
}
