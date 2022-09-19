package com.usermanagement.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.usermanagement.exception.ResourceAlreadyExistException;
import com.usermanagement.exception.ResourceNotFoundException;
import com.usermanagement.model.dtos.RoleDTO;
import com.usermanagement.model.dtos.UserDTO;
import com.usermanagement.model.entities.Role;
import com.usermanagement.model.entities.User;
import com.usermanagement.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private UserServiceImpl userService;
	
	@Mock
	private RoleServiceImpl roleService;
	
	@BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }
	
	private User populateUser() {
		Set<Role> roles = new HashSet<>();
		Role role = new Role();
		role.setName("ADMIN");
		role.setDescription("ROLE_ADMIN");
		roles.add(role);
		
		User user = new User();
		user.setId(1L);
		user.setFirstName("Pooja");
		user.setEmailId("abc@gmail.com");
		user.setPassword("Admin@12345678");
		user.setRoles(roles);
		return user;
	}
	
	public UserDTO populateUserDTO() {
		Set<RoleDTO> roles = new HashSet<>();
		roles.add(new RoleDTO("USER", "ROLE_USER", null, true));
		return new UserDTO("Pooja", "Gupta", "pooja.gupta@gmail.com",  "Abc@1234567",roles);
	}
	
	@Test
	public void test_saveUser() throws ResourceAlreadyExistException {
		
		User user = populateUser();
		when(userRepository.existsByEmailId(anyString())).thenReturn(false);
		when(roleService.findByNameIn(anyList())).thenReturn(user.getRoles());
        when(userRepository.save(any())).thenReturn(user);
        User savedUser = userService.save(populateUserDTO());
        assertThat(savedUser).isNotNull();
	}
	
	@Test
	void testUpdateUser_EmailIdAlreadyExistException()
	{
		User user = populateUser();
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
		when(userRepository.existsUserByEmailId(anyLong(),anyString())).thenReturn(true);

		assertThrows(ResourceAlreadyExistException.class, () -> {
			userService.update(1L, populateUserDTO());
		});
	}
	
	@Test
	void testGetUser_IdNotFoundException()
	{
		User user = populateUser();
		when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class,
				() -> userService.findById(user.getId()),
				"User not found with id:"+user.getId());
		
		assertThat(HttpStatus.NOT_FOUND);
	}

}
