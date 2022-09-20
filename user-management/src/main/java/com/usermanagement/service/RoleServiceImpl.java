package com.usermanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.usermanagement.exception.ResourceAlreadyExistException;
import com.usermanagement.exception.ResourceNotFoundException;
import com.usermanagement.model.dtos.PermissionDTO;
import com.usermanagement.model.dtos.RoleDTO;
import com.usermanagement.model.entities.Role;
import com.usermanagement.repository.RoleRepository;
import com.usermanagement.service.interfaces.PermissionService;
import com.usermanagement.service.interfaces.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PermissionService permissionService;

	@Override
	public Role save(RoleDTO roleDto) throws ResourceAlreadyExistException {
		
		boolean isExist = roleRepository.existsByName(roleDto.getName());
		if(isExist) {
			throw new ResourceAlreadyExistException("Role already exist with name: " +roleDto.getName());
		}
		Role roleToBeSaved = modelMapper.map(roleDto, Role.class);
		 if(!CollectionUtils.isEmpty(roleDto.getPermissions())) {
	        	List<String> permissionNames = roleDto.getPermissions().stream().map(PermissionDTO :: getName).collect(Collectors.toList());
	        	roleToBeSaved.setPermissions(permissionService.findByNameIn(permissionNames));
	        }
		return roleRepository.save(roleToBeSaved);
	}

	@Override
	public void delete(Long id) {
		roleRepository.deleteById(id);
		
	}

	@Override
	public List<Role> findAll() {
        return roleRepository.findAll();
	}

	@Override
	public Role updateRole(Long id, RoleDTO roleDTO) throws ResourceNotFoundException, ResourceAlreadyExistException {
		
		Role roleToUpdate = findById(id);
		if (roleDTO.getName() != null) {
			boolean isRoleAlreadyExist = roleRepository.existsByIdAndName(roleToUpdate.getId() ,roleDTO.getName());
        	
        	if(isRoleAlreadyExist) {
        		throw new ResourceAlreadyExistException(
						"Role with name: " + roleDTO.getName() + " already exist");
        	}else {
        		roleToUpdate.setName(roleDTO.getName());
        	}
		}

		if (roleDTO.getDescription() != null) {
			roleToUpdate.setDescription(roleDTO.getDescription());
		}

		roleToUpdate.setDefault(roleDTO.isDefault());

		if (!CollectionUtils.isEmpty(roleDTO.getPermissions())) {
			List<String> permissionNames = roleDTO.getPermissions().stream().map(PermissionDTO::getName)
					.collect(Collectors.toList());
			roleToUpdate.setPermissions(permissionService.findByNameIn(permissionNames));
		}

        return roleRepository.save(roleToUpdate);
	}

	@Override
	public Role findById(Long id) throws ResourceNotFoundException {
		return roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role is not found with id: " + id));
	}

	@Override
	public Set<Role> findByNameIn(List<String> roleNames) {
		return roleRepository.findByNameIn(roleNames);
	}

	@Override
	public Role findByDefaultRole() {
		return roleRepository.findByIsDefaultTrue();
	}

}
