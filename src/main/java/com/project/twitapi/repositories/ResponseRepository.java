package com.project.twitapi.repositories;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.twitapi.models.Response;

@Repository
public interface ResponseRepository extends MongoRepository<Response, String> {
	}