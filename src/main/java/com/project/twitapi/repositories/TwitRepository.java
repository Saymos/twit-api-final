package com.project.twitapi.repositories;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.twitapi.models.Twit;

@Repository
public interface TwitRepository extends MongoRepository<Twit, String> {
	}