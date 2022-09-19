package com.usermanagement.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usermanagement.model.dtos.RoleDTO;
import com.usermanagement.model.dtos.UserDTO;
import com.usermanagement.model.entities.User;
import com.usermanagement.service.interfaces.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
	
	@MockBean
	private UserService userService;
	
	@Autowired
	private MockMvc mvc;
	
	private static ObjectMapper objectMapper = new ObjectMapper();

	
	public UserDTO populateUserDTO() {
		Set<RoleDTO> roles = new HashSet<>();
		roles.add(new RoleDTO("USER", "ROLE_USER", null, true));
		return new UserDTO("Pooja", "Gupta", "pooja.gupta@gmail.com",  "Abc@1234567",roles);
	}
	
	@Test
	public void testCreateUser() throws Exception {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		User user = mapper.map(populateUserDTO(), User.class);
		when(userService.save(any(UserDTO.class))).thenReturn(user);

		String json = objectMapper.writeValueAsString(user);
		mvc.perform(MockMvcRequestBuilders.post("/api/users/").contentType(MediaType.APPLICATION_JSON)
		.content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName").value("Pooja"))
		.andExpect(jsonPath("$.password").value("Abc@1234567"))
		.andExpect(jsonPath("$.emailId").value("pooja.gupta@gmail.com"));

	}
	
	
	@Test
    public void testUpdateUser() throws Exception {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		User user = mapper.map(populateUserDTO(), User.class);
		user.setPassword("Aertyuihv1322@");
		when(userService.update(anyLong() ,any(UserDTO.class))).thenReturn(user);
		
		String json = objectMapper.writeValueAsString(user);
		mvc.perform((MockMvcRequestBuilders.put("/api/users/{id}",1L)).contentType(MediaType.APPLICATION_JSON)
                .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$.password").value("Aertyuihv1322@"));
    }
 
    @Test
    public void testDeleteUser() throws Exception {
    	doNothing().when(userService).delete(ArgumentMatchers.anyLong());
    	String result = mvc.perform((MockMvcRequestBuilders.delete("/api/users/{id}", 1L))).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        assertEquals(result, "User deleted successfully");
    }
	
	@Test
	void test_OneUser() throws Exception {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		User user = mapper.map(populateUserDTO(), User.class);
	       when(userService.findById(1L)).thenReturn(user);

	       mvc.perform(MockMvcRequestBuilders.get("/api/users/"+ 1L)).andExpect(status().isOk())
           .andExpect(jsonPath("$.firstName").value("Pooja"));
	}
	
	@Test
	void test_getAllUsers() throws Exception {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		List<User> list = new ArrayList<>();
		User user = mapper.map(populateUserDTO(), User.class);
		list.add(user);
		
		when(userService.findAll()).thenReturn(list);

		mvc.perform(MockMvcRequestBuilders.get("/api/users")).andDo(MockMvcResultHandlers.print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].roles").exists());
	}
}
