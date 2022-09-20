package com.usermanagement.service.interfaces;

import java.util.List;
import java.util.Set;

import com.usermanagement.exception.ResourceAlreadyExistException;
import com.usermanagement.exception.ResourceNotFoundException;
import com.usermanagement.model.dtos.RoleDTO;
import com.usermanagement.model.entities.Role;

public interface RoleService {

	Role save(RoleDTO roleDto) throws ResourceAlreadyExistException;

    	List<Role> findAll();

    	void delete(Long id);
    
    	Role updateRole(Long id, RoleDTO roleDto) throws ResourceNotFoundException, ResourceAlreadyExistException;

    	Role findById(Long id) throws ResourceNotFoundException;

   	Set<Role> findByNameIn(List<String> roleNames);

   	Role findByDefaultRole();

	
}
