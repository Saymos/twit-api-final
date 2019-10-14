package com.project.twitapi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.twitapi.exceptions.DuplicateEntryException;
import com.project.twitapi.exceptions.EntryNotFoundException;
import com.project.twitapi.exceptions.NotAllowedException;
import com.project.twitapi.models.Twit;
import com.project.twitapi.models.User;
import com.project.twitapi.repositories.TwitRepository;
import com.project.twitapi.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private ServiceImplUtil util;
	@Autowired
	private TwitRepository twitRepo;
	@Autowired
	private UserRepository userRepo;

	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	public User getUser(String id) {
		return util.checkUserExists(userRepo, id);
	}

	public User createUser(User user) {
		if (user.getId() != null) {
			Optional<User> userInDb = userRepo.findById(user.getId());
			if (userInDb.isPresent()) {
				throw new DuplicateEntryException(user.getId());
			}
		}
		userRepo.save(user);
		return user;
	}

	public void deleteUser(String id) {
		Optional<User> user = userRepo.findById(id);
		user.orElseThrow(() -> new EntryNotFoundException(id));
		userRepo.deleteById(id);
	}

	public void addFollower(String id, String followerUserId) {
		User user = util.checkUserExists(userRepo, id);
		
		if (user.getFollowersUserId() == null) {
			user.setFollowersUserId(new ArrayList<String>());
			
		} else if (user.getFollowersUserId().contains(followerUserId)) {
			throw new DuplicateEntryException(followerUserId);
			
		} else if (id.equalsIgnoreCase(followerUserId)) {
			throw new NotAllowedException("Nono");
		}
		
		user.getFollowersUserId().add(followerUserId);
		userRepo.save(user);
	}

	public void removeFollower(String id, String followerUserId) {
		User user = util.checkUserExists(userRepo, id);
		user.getFollowersUserId().remove(followerUserId);
		userRepo.save(user);
	}

	public void addTwit(String id, String twitId) {
		User user = util.checkUserExists(userRepo, id);
		Twit twit = util.checkTwitExists(twitRepo, twitId);
		user.getTwits().add(twit);
		userRepo.save(user);
	}

	public void removeTwitFromUser(String id, String twitId) {
		
		User user = util.checkUserExists(userRepo, id);
		util.checkTwitExists(twitRepo, twitId);
		if (user.getTwits().stream().filter(t -> t.getId().equalsIgnoreCase(twitId)) != null) {
			user.getTwits().removeIf(t -> t.getId().equalsIgnoreCase(twitId));
			userRepo.save(user);
		} else {
			throw new EntryNotFoundException(twitId);
		}
	}

	public List<Twit> getUserTwits(String id) {
		User user = util.checkUserExists(userRepo, id);
		return user.getTwits();
	}

	public List<User> getFollowers(String id) {
		User user = util.checkUserExists(userRepo, id);
		List<User> followerList = new ArrayList<User>();
		for (String followerId : user.getFollowersUserId()) {
			User follower = util.checkUserExists(userRepo, followerId);
			followerList.add(follower);
		}
		return followerList;
	}
}