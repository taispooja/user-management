package com.usermanagement.controller;

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
import com.usermanagement.model.entities.User;
import com.usermanagement.service.interfaces.RoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.usermanagement.model.dtos.RoleDTO;
import com.usermanagement.model.entities.Role;
import static com.usermanagement.util.Constansts.*;

@Api(tags = SWG_ROLE_TAG_NAME, description = SWG_ROLE_TAG_DESCRIPTION)
@RestController
@RequestMapping(value = "/api/roles")
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@ApiOperation(value = SWG_ROLE_CREATE_OPERATION)
	@PostMapping
	public Role createRole(@Valid @RequestBody RoleDTO roleDto) throws ResourceAlreadyExistException{
        return roleService.save(roleDto);
    }
	
	@ApiOperation(value = SWG_ROLE_LIST_OPERATION)
	@GetMapping
	public List<Role> getAllRoles() {
		return roleService.findAll();
	}
	
	@ApiOperation(value = SWG_ROLE_ITEM_OPERATION)
	@GetMapping("/{id}")
	public Role getRoleById(@PathVariable Long id) throws ResourceNotFoundException {
		return roleService.findById(id);

	}
	
	@ApiOperation(value = SWG_ROLE_UPDATE_OPERATION)
	@PutMapping("/{id}")
	public Role updateRole(@PathVariable Long id, @RequestBody RoleDTO roleDto) throws ResourceNotFoundException, ResourceAlreadyExistException {
		return roleService.updateRole(id, roleDto);
	}
	
	@ApiOperation(value = SWG_ROLE_DELETE_OPERATION)
	@DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Long id) {
        roleService.delete(id);
        return new ResponseEntity<>("Role deleted", HttpStatus.OK);
    }

}
