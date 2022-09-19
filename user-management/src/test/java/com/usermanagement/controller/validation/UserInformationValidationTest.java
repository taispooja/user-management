package com.usermanagement.controller.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.AfterClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.event.annotation.AfterTestClass;

import com.usermanagement.exception.ResourceAlreadyExistException;
import com.usermanagement.model.dtos.RoleDTO;
import com.usermanagement.model.dtos.UserDTO;

@ExtendWith(MockitoExtension.class)
public class UserInformationValidationTest {

	
	private static ValidatorFactory validatorFactory;
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();;
 
    @AfterTestClass
    public static void close() {
        validatorFactory.close();
    }
    
    
    private UserDTO populateUserDTO() {
		Set<RoleDTO> roles = new HashSet<>();
		roles.add(new RoleDTO("ADMIN", "ROLE_ADMIN", null,false));
		return new UserDTO("Pooja", "Gupta", "pooja.gupta@gmail.com",  "Abc@1234567",roles);
	}
    
    @Test
	public void test_validateEmail() throws ResourceAlreadyExistException {
		UserDTO userDTO = populateUserDTO();
		userDTO.setEmailId(null);
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
        assertEquals(1, violations.size());
        assertEquals("The email address is required", violations.iterator().next().getMessage());
	}
	
	@Test
	public void test_validateInvalidEmail() throws ResourceAlreadyExistException {
		UserDTO userDTO = populateUserDTO();
		userDTO.setEmailId("pooja1123123");
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
        assertEquals(1, violations.size());
        assertEquals("Email address is not valid", violations.iterator().next().getMessage());
	}
	
	@Test
	public void test_validatePassword() throws ResourceAlreadyExistException {
		UserDTO userDTO = populateUserDTO();
		userDTO.setPassword(null);
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
        assertEquals(1, violations.size());
        assertEquals("Password is mandatory", violations.iterator().next().getMessage());
	}
	
	@Test
	public void test_validateInvalidPassword() throws ResourceAlreadyExistException {
		UserDTO userDTO = populateUserDTO();
		 userDTO.setPassword("1233dsada");
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
        assertEquals(1, violations.size());
        assertEquals("Password Must be at least 8 characters long and it should contain atleat 1 upperCase , 1 lowerCase, 1 number and 1 special character", violations.iterator().next().getMessage());
	}
	
	@Test
	public void test_validateUserInfo() throws ResourceAlreadyExistException {
		UserDTO userDTO = populateUserDTO();
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
        assertEquals(0, violations.size());
	}
}
