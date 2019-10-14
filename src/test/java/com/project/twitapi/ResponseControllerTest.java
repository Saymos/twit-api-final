package com.project.twitapi;

import static com.project.twitapi.TestUtil.asJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.project.twitapi.controllers.ResponseController;
import com.project.twitapi.models.Response;
import com.project.twitapi.services.ResponseService;

@RunWith(SpringRunner.class)
@WebMvcTest(ResponseController.class)
public class ResponseControllerTest {

	@Autowired
	private MockMvc mockMvc;

	// @MockBean
	// private TwitService twitService;
	@MockBean
	private ResponseService responseService;

	@Test
	public void getResponseTest() throws Exception {

		Response response = new Response();
		response.setId("1");
		response.setResponse("response");
		response.setUserId("userId");

		when(responseService.getResponse("1")).thenReturn(response);

		mockMvc.perform(get("/response/{id}", "1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(response.getId())))
				.andExpect(jsonPath("response", is(response.getResponse())));
	}

	@Test
	public void CreateResponseTest() throws Exception {
		Response response = new Response();
		response.setId("1");
		response.setResponse("response");
		response.setUserId("userId");
		
		mockMvc.perform(post("/response").contentType(MediaType.APPLICATION_JSON).content(asJson(response)))
			.andExpect(status().isCreated()).andExpect(jsonPath("response", is("response")));
	}
	
	@Test
	public void deleteResponseTest() throws Exception {
		Response response = new Response();
		response.setId("1");
		response.setResponse("response");
		response.setUserId("userId");
		
		ArrayList<Response> responses = new ArrayList<Response>();
		responses.add(response);
		
		assertThat(responses.size() == 1);
		
		mockMvc.perform(delete("/response/{id}", "1")).andExpect(status().isNoContent());
		
		assertThat(responses.size() == 0);
	}
}
