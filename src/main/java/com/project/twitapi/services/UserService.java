package com.project.twitapi.services;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.project.twitapi.models.Twit;
import com.project.twitapi.models.User;

@Repository
public interface UserService {

	public List<User> getAllUsers();
	public User getUser(String id);
	public User createUser(User user);

	public void deleteUser(String id);

	public void addFollower(String id, String followerUserId);

	public void removeFollower(String id, String followerUserId);
	public void addTwit(String id, String twitId);
	public void removeTwitFromUser(String id, String twitId);
	public List<Twit> getUserTwits(String id);
	public List<User> getFollowers(String id);
}