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

import com.project.twitapi.controllers.TwitController;
import com.project.twitapi.models.Response;
import com.project.twitapi.models.Twit;
import com.project.twitapi.services.ResponseService;
import com.project.twitapi.services.TwitService;

@RunWith(SpringRunner.class)
@WebMvcTest(TwitController.class)
public class TwitControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TwitService twitService;
	@MockBean
	private ResponseService responseService;

	@Test
	public void getTwitTest() throws Exception {

		Twit twit = new Twit();
		twit.setId("1");
		twit.setResponses(new ArrayList<Response>());
		twit.setTwit("twit");

		when(twitService.getTwit("1")).thenReturn(twit);

		mockMvc.perform(get("/twit/{id}", "1")).andExpect(status().isOk()).andExpect(jsonPath("$.id", is(twit.getId())))
				.andExpect(jsonPath("$.twit", is(twit.getTwit())));
	}

	@Test
	public void CreateTwitTest() throws Exception {
		Twit twit = new Twit();
		twit.setId("1");
		twit.setResponses(new ArrayList<Response>());
		twit.setTwit("twit");

		mockMvc.perform(
				post("/twit").contentType(MediaType.APPLICATION_JSON).content(asJson(twit)))
				.andExpect(status().isCreated()).andExpect(jsonPath("twit", is("twit")));
	}

	@Test
	public void deletetTwitTest() throws Exception {
		Twit twit = new Twit();
		twit.setId("1");
		twit.setResponses(new ArrayList<Response>());
		twit.setTwit("twit");
		ArrayList<Twit> twits = new ArrayList<Twit>();
		twits.add(twit);

		assertThat(twits.size() == 1);

		mockMvc.perform(delete("/twit/{id}", "1")).andExpect(status().isNoContent());

		assertThat(twits.size() == 0);
	}

	@Test
	public void addTwitTest() throws Exception {

		Twit twit = new Twit();
		twit.setId("1");
		twit.setResponses(new ArrayList<Response>());
		twit.setTwit("twit");

		Response response = new Response();
		response.setId("1");
		response.setResponse("response");
		response.setUserId("userId");

		assertThat(twit.getResponses().size() == 0);
		
		when(twitService.getTwit("1")).thenReturn(twit);
		when(responseService.getResponse("1")).thenReturn(response);

		mockMvc.perform(put("/twit/addResponse/{id}", "1").contentType(MediaType.APPLICATION_JSON)
				.content(asJson(response.getId()))).andExpect(status().isOk());

		assertThat(twit.getResponses().size() == 1);
	}
	
	@Test
	public void removeTwitTest() throws Exception {
		Response response = new Response();
		response.setId("1");
		response.setResponse("response");
		response.setUserId("userId");

		Twit twit = new Twit();
		twit.setId("1");
		twit.setResponses(new ArrayList<Response>());
		twit.setTwit("twit");
		twit.getResponses().add(response);
		
		assertThat(twit.getResponses().size() == 1);

		when(twitService.getTwit("1")).thenReturn(twit);

		mockMvc.perform(put("/twit/removeResponse/{id}", "1").contentType(MediaType.APPLICATION_JSON)
				.content(asJson(response.getId()))).andExpect(status().isOk());

		assertThat(twit.getResponses().size() == 0);
	}
	
	@Test
	public void getAllResponsesOnTwitTest() throws Exception {
		Response expectedResponse1 = new Response();
		expectedResponse1.setId("1");
		expectedResponse1.setResponse("response");
		expectedResponse1.setUserId("userId");

		Response expectedResponse2 = new Response();
		expectedResponse2.setId("1");
		expectedResponse2.setResponse("response");
		expectedResponse2.setUserId("userId");

		ArrayList<Response> expectedResponses = new ArrayList<Response>(
				Stream.of(expectedResponse1, expectedResponse2).collect(Collectors.toList()));
		
		when(twitService.getResponsesOnTwit(anyString())).thenReturn(expectedResponses);
		
		this.mockMvc.perform(get("/twit/getResponsesOnTwit/{id}", "1")).andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(2)))
		.andExpect(jsonPath("$[0].id", is(expectedResponse1.getId())))
		.andExpect(jsonPath("$[0].response", is(expectedResponse1.getResponse())))
		.andExpect(jsonPath("$[1].id", is(expectedResponse2.getId())))
		.andExpect(jsonPath("$[1].response", is(expectedResponse2.getResponse())));
	}
}
