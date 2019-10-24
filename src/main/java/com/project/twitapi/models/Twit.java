package com.project.twitapi.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document()
public class Twit {
	
	@Id
	private String id;
	
	private String twit;
	private List<Response> responses = new ArrayList<Response>();
	private List<String> likes = new ArrayList<String>();
}
