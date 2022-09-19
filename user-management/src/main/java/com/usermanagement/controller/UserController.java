package com.usermanagement.controller;

import static com.usermanagement.util.Constansts.SWG_USER_CREATE_OPERATION;
import static com.usermanagement.util.Constansts.SWG_USER_DELETE_OPERATION;
import static com.usermanagement.util.Constansts.SWG_USER_ITEM_OPERATION;
import static com.usermanagement.util.Constansts.SWG_USER_LIST_OPERATION;
import static com.usermanagement.util.Constansts.SWG_USER_TAG_DESCRIPTION;
import static com.usermanagement.util.Constansts.SWG_USER_TAG_NAME;
import static com.usermanagement.util.Constansts.SWG_USER_UPDATE_OPERATION;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usermanagement.exception.ResourceAlreadyExistException;
import com.usermanagement.exception.ResourceNotFoundException;
import com.usermanagement.model.dtos.UserDTO;
import com.usermanagement.model.entities.User;
import com.usermanagement.service.interfaces.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = SWG_USER_TAG_NAME,  description = SWG_USER_TAG_DESCRIPTION)
@RestController
@RequestMapping(value = "/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@ApiOperation(value = SWG_USER_CREATE_OPERATION)
	@PostMapping
	public User createUser(@Valid @RequestBody UserDTO userDto) throws ResourceAlreadyExistException {
        return userService.save(userDto);
    }
	
	@ApiOperation(value = SWG_USER_LIST_OPERATION)
	@GetMapping
	public List<User> getAllUsers() {
		return userService.findAll();
	}
	
	@ApiOperation(value = SWG_USER_ITEM_OPERATION)
	@GetMapping("/{id}")
	public User getUserById(@PathVariable Long id) throws ResourceNotFoundException {
		return userService.findById(id);

	}
	
	@ApiOperation(value = SWG_USER_UPDATE_OPERATION)
	@PutMapping("/{id}")
	public User updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDto) throws ResourceNotFoundException, ResourceAlreadyExistException {
		return userService.update(id, userDto);
	}
	
	@ApiOperation(value = SWG_USER_DELETE_OPERATION)
	@DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

}
