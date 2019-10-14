package com.project.twitapi;

import static com.project.twitapi.TestUtil.asJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.project.twitapi.controllers.UserController;
import com.project.twitapi.models.Response;
import com.project.twitapi.models.Twit;
import com.project.twitapi.models.User;
import com.project.twitapi.services.TwitService;
import com.project.twitapi.services.UserService;

@RunWith(SpringRunner.class)
 @WebMvcTest(UserController.class)
// TODO REMOVE
//@SpringBootTest
//@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;
	@MockBean
	private TwitService twitService;

	@Test
	public void getAllUsersTest() throws Exception {

		User user = new User();
		user.setId("1");
		user.setUserName("userName");
		user.setFollowersUserId(new ArrayList<String>(Stream.of("follower").collect(Collectors.toList())));
		user.setTwits(new ArrayList<Twit>());

		List<User> expectedUserList = new ArrayList<User>(Stream.of(user).collect(Collectors.toList()));
		when(userService.getAllUsers()).thenReturn(expectedUserList);

		this.mockMvc.perform(get("/user")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].id", is(user.getId())))
				.andExpect(jsonPath("$[0].userName", is(user.getUserName())))
				.andExpect(jsonPath("$[0].followersUserId", is(user.getFollowersUserId())))
				.andExpect(jsonPath("$[0].twits", is(user.getTwits())));
	}

	@Test
	public void getUserTest() throws Exception {

		User expectedUser = new User();
		expectedUser.setId("1");
		expectedUser.setUserName("userName");
		expectedUser.setFollowersUserId(new ArrayList<String>(Stream.of("follower").collect(Collectors.toList())));
		expectedUser.setTwits(new ArrayList<Twit>());

		when(userService.getUser("1")).thenReturn(expectedUser);

		mockMvc.perform(get("/user/{id}", "1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(expectedUser.getId())))
				.andExpect(jsonPath("$.userName", is(expectedUser.getUserName())))
				.andExpect(jsonPath("$.followersUserId", is(expectedUser.getFollowersUserId())))
				.andExpect(jsonPath("$.twits", is(expectedUser.getTwits())));
	}

	@Test
	public void createUserTest() throws Exception {
		User user = new User();
		user.setId("1");
		user.setUserName("userName");
		user.setFollowersUserId(new ArrayList<String>());
		user.setTwits(new ArrayList<Twit>());

		mockMvc.perform(post("/user/").contentType(MediaType.APPLICATION_JSON).content(asJson(user)))
				.andExpect(status().isCreated()).andExpect(jsonPath("id", is("1")))
				.andExpect(jsonPath("userName", is("userName")));

	}

	@Test
	public void deleteUserTest() throws Exception {
		User user = new User();
		user.setId("1");
		user.setUserName("userName");
		ArrayList<User> users = new ArrayList<User>();
		users.add(user);

		assertThat(users.size() == 1);

		mockMvc.perform(delete("/user/{id}", "1")).andExpect(status().isNoContent());

		assertThat(users.size() == 0);
	}

	@Test
	public void addFollowerTest() throws Exception {
		User user = new User();
		user.setId("1");
		user.setUserName("userName");
		user.setFollowersUserId(new ArrayList<String>());
		user.setTwits(new ArrayList<Twit>());

		String followerId = "followerId";

		assertThat(user.getFollowersUserId().size() == 0);
		when(userService.getUser("1")).thenReturn(user);

		mockMvc.perform(
				put("/user/addFollower/{id}", "1").contentType(MediaType.APPLICATION_JSON).content(asJson(followerId)))
				.andExpect(status().isOk());
		assertThat(user.getFollowersUserId().size() == 1);
	}

	@Test
	public void removeFollowerTest() throws Exception {
		String followerId = "followerId";
		User user = new User();
		user.setId("1");
		user.setUserName("userName");
		user.setFollowersUserId(new ArrayList<String>());
		user.getFollowersUserId().add(followerId);
		user.setTwits(new ArrayList<Twit>());

		assertThat(user.getFollowersUserId().size() == 1);

		when(userService.getUser("1")).thenReturn(user).thenReturn(user);

		mockMvc.perform(put("/user/removeFollower/{id}", "1").contentType(MediaType.APPLICATION_JSON)
				.content(asJson(followerId))).andExpect(status().isOk());
		assertThat(user.getFollowersUserId().size() == 0);
	}

	@Test
	public void addTwitTest() throws Exception {
		User user = new User();
		user.setId("1");
		user.setUserName("userName");
		user.setFollowersUserId(new ArrayList<String>());
		user.setTwits(new ArrayList<Twit>());

		Twit twit = new Twit();
		twit.setId("1");
		twit.setResponses(new ArrayList<Response>());
		twit.setTwit("twit");

		when(userService.getUser("1")).thenReturn(user);
		when(twitService.getTwit("1")).thenReturn(twit);

		mockMvc.perform(
				put("/user/addTwit/{id}", "1").contentType(MediaType.APPLICATION_JSON).content(asJson(twit.getId())))
				.andExpect(status().isOk());

		assertThat(user.getTwits().size() == 1);
	}

	@Test
	public void removeTwitTest() throws Exception {

		Twit twit = new Twit();
		twit.setId("1");
		twit.setResponses(new ArrayList<Response>());
		twit.setTwit("twit");

		User user = new User();
		user.setId("1");
		user.setUserName("userName");
		user.setFollowersUserId(new ArrayList<String>());
		user.getTwits().add(twit);
		user.setTwits(new ArrayList<Twit>());
		
		assertThat(user.getTwits().size() == 1);

		when(userService.getUser("1")).thenReturn(user).thenReturn(user);

		mockMvc.perform(put("/user/removeTwitFromUser/{id}", "1").contentType(MediaType.APPLICATION_JSON)
				.content(asJson(twit.getId()))).andExpect(status().isOk());
		assertThat(user.getTwits().size() == 0);
	}

	@Test
	public void getUserFollowersTest() throws Exception {

		User expectedFollower1 = new User();
		expectedFollower1.setId("1");
		expectedFollower1.setUserName("Follower1");
		expectedFollower1.setFollowersUserId(new ArrayList<String>());
		expectedFollower1.setTwits(new ArrayList<Twit>());

		User expectedFollower2 = new User();
		expectedFollower2.setId("2");
		expectedFollower2.setUserName("Follower2");
		expectedFollower2.setFollowersUserId(new ArrayList<String>());
		expectedFollower2.setTwits(new ArrayList<Twit>());

		ArrayList<User> expectedFollowers = new ArrayList<User>(
				Stream.of(expectedFollower1, expectedFollower2).collect(Collectors.toList()));
		when(userService.getFollowers(anyString())).thenReturn(expectedFollowers);

		this.mockMvc.perform(get("/user/getFollowers/{id}", "1")).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].id", is(expectedFollower1.getId())))
				.andExpect(jsonPath("$[0].userName", is(expectedFollower1.getUserName())))
				.andExpect(jsonPath("$[1].id", is(expectedFollower2.getId())))
				.andExpect(jsonPath("$[1].userName", is(expectedFollower2.getUserName())));
	}

	@Test
	public void getUserTwitsTest() throws Exception {
		Twit expectedTwit1 = new Twit();
		expectedTwit1.setId("1");
		expectedTwit1.setTwit("twit");
		expectedTwit1.setResponses(new ArrayList<Response>());

		Twit expectedTwit2 = new Twit();
		expectedTwit2.setId("1");
		expectedTwit2.setTwit("twit");
		expectedTwit2.setResponses(new ArrayList<Response>());

		ArrayList<Twit> expectedTwits = new ArrayList<Twit>(
				Stream.of(expectedTwit1, expectedTwit2).collect(Collectors.toList()));
		
		when(userService.getUserTwits(anyString())).thenReturn(expectedTwits);
		
		this.mockMvc.perform(get("/user/getUserTwits/{id}", "1")).andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(2)))
		.andExpect(jsonPath("$[0].id", is(expectedTwit1.getId())))
		.andExpect(jsonPath("$[0].twit", is(expectedTwit1.getTwit())))
		.andExpect(jsonPath("$[1].id", is(expectedTwit2.getId())))
		.andExpect(jsonPath("$[1].twit", is(expectedTwit2.getTwit())));
	}

}