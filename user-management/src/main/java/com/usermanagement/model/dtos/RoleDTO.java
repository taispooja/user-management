package com.usermanagement.model.dtos;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RoleDTO {

	@NotEmpty(message = "Role name is mandatory")
	private String name;
	private String description;
	private List<PermissionDTO> permissions;
	@JsonProperty(value = "default")
	private boolean isDefault;

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<PermissionDTO> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<PermissionDTO> permissions) {
		this.permissions = permissions;
	}

	public RoleDTO(String name, String description,
			List<PermissionDTO> permissions, boolean isDefault) {
		super();
		this.name = name;
		this.description = description;
		this.permissions = permissions;
		this.isDefault = isDefault;
	}

	
}
