package com.project.twitapi.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Document()
public class User  {
	
	@Id
	private String id;
	
	private String userName;
	private List<String> followersUserId = new ArrayList<String>();
	private List<Twit> twits = new ArrayList<Twit>();
}