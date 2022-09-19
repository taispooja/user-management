package com.usermanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usermanagement.model.entities.Permissions;
import com.usermanagement.repository.PermissionRepository;
import com.usermanagement.service.interfaces.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService{
	
	@Autowired
	PermissionRepository permissionRepository;

	@Override
	public void savePermission(Set<Permissions> permissions) {

		permissions.parallelStream().forEach(x -> {
	      Optional<Permissions> permission = permissionRepository.findByName(x.getName());

	      if (permission.isEmpty()) {
	        permissionRepository.save(x);
	      } 
	    });
	}

	@Override
	public Set<Permissions> findByNameIn(List<String> permissionNames) {
		return permissionRepository.findByNameIn(permissionNames);
	}
}
