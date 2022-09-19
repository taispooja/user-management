package com.usermanagement.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usermanagement.model.entities.Permissions;

@Repository
public interface PermissionRepository extends JpaRepository<Permissions, Long> {

	Optional<Permissions> findByName(String name);

	Set<Permissions> findByNameIn(List<String> permissionNames);

}
