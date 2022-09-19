package com.usermanagement.bootstrap;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.usermanagement.model.entities.Permissions;
import com.usermanagement.service.interfaces.PermissionService;

@Component
public class DataLoad implements ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
	private PermissionService permissionService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		loadPermission();
		
	}

	private void loadPermission() {
		Set<Permissions> permissions = new HashSet<>();
		Permissions permission1 = new Permissions();
		permission1.setName("read:users");
		permission1.setDescription("Retrieve the list of users");
		
		Permissions permission2 = new Permissions();
		permission2.setName("update:user");
		permission2.setDescription("Update user's information");
		
		Permissions permission3 = new Permissions();
		permission3.setName("delete:user");
		permission3.setDescription("Delete a user");
		
		Permissions permission4 = new Permissions();
		permission4.setName("create:user");
		permission4.setDescription("Create a user");
		
		permissions.add(permission1);
		permissions.add(permission2);
		permissions.add(permission3);
		permissions.add(permission4);
		
		permissionService.savePermission(permissions);
	}
	
	

}
