package com.usermanagement.service.interfaces;

import java.util.List;

import com.usermanagement.exception.ResourceAlreadyExistException;
import com.usermanagement.exception.ResourceNotFoundException;
import com.usermanagement.model.dtos.UserDTO;
import com.usermanagement.model.entities.User;

public interface UserService {
	
	User save(UserDTO userDto) throws ResourceAlreadyExistException;

	List<User> findAll();

	void delete(Long id);

	User update(Long id, UserDTO userDto) throws ResourceNotFoundException, ResourceAlreadyExistException;

	User findById(Long id) throws ResourceNotFoundException;

}
