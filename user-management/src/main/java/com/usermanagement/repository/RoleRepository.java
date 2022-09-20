package com.usermanagement.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.usermanagement.model.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(String name);

	boolean existsByName(String name);

	Set<Role> findByNameIn(List<String> roleNames);

	Role findByIsDefaultTrue();

	@Query("select case when count(r)> 0 then true else false end from Role r where r.name= :name and id != :id")
	boolean existsByIdAndName(@Param("id") Long id, @Param("name") String name);

}
