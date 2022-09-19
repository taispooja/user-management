package com.usermanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.usermanagement.model.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByEmailId(String emailId);

	boolean existsByEmailId(String email);

	@Query("select case when count(u)> 0 then true else false end from User u where u.emailId= :email and id != :id")
	boolean existsUserByEmailId(@Param("id") Long id ,@Param("email") String emailId);
	
}
