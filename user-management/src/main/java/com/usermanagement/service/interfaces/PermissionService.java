package com.usermanagement.service.interfaces;

import java.util.List;
import java.util.Set;

import com.usermanagement.model.entities.Permissions;

public interface PermissionService {

	void savePermission(Set<Permissions> permissions);

	Set<Permissions> findByNameIn(List<String> permissionNames);

}
