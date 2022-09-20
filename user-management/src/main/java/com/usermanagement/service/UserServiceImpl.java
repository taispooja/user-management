package com.usermanagement.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.usermanagement.exception.ResourceAlreadyExistException;
import com.usermanagement.exception.ResourceNotFoundException;
import com.usermanagement.model.dtos.RoleDTO;
import com.usermanagement.model.dtos.UserDTO;
import com.usermanagement.model.entities.User;
import com.usermanagement.repository.UserRepository;
import com.usermanagement.service.interfaces.RoleService;
import com.usermanagement.service.interfaces.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	

	@Autowired
	private RoleService roleService;

	@Override
    public User save(UserDTO userDto) throws ResourceAlreadyExistException {
        User newUser = new User();
        if(userDto.getEmailId() != null) {
        	boolean isUserAlreadyExist = userRepository.existsByEmailId(userDto.getEmailId());
        	
        	if(isUserAlreadyExist) {
        		throw new ResourceAlreadyExistException(
						"User with emailId: " + userDto.getEmailId() + " already exist");
        	}else {
        		newUser.setEmailId(userDto.getEmailId());    
        	}
        }
        newUser.setFirstName(userDto.getFirstName());
        newUser.setLastName(userDto.getLastName());
        //newUser.setPassword(Base64.getEncoder().encodeToString(userDto.getPassword().getBytes()));
        newUser.setPassword(userDto.getPassword());
        
        if(!CollectionUtils.isEmpty(userDto.getRoles())) {
        	List<String> roleNames = userDto.getRoles().stream().map(RoleDTO :: getName).collect(Collectors.toList());
        	newUser.setRoles(roleService.findByNameIn(roleNames));
        }else {
        	newUser.setRoles(new HashSet<>(Arrays.asList(roleService.findByDefaultRole())));
        }

        return userRepository.save(newUser);
    }
	
	@Override
    public List<User> findAll() {
       return userRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
    
    @Override
    public User update(Long id, UserDTO userDto) throws ResourceNotFoundException, ResourceAlreadyExistException {
        User user = findById(id);

    	if(userDto.getFirstName() != null) {
            user.setFirstName(userDto.getFirstName());
        }
    	
        if(userDto.getLastName() != null) {
            user.setLastName(userDto.getLastName());
        }
        
        if(userDto.getPassword() != null) {
           // user.setPassword(Base64.getEncoder().encodeToString(userDto.getPassword().getBytes()));
        	user.setPassword(userDto.getPassword());
        }
        
        if(userDto.getEmailId() != null) {
        	boolean isUserAlreadyExist = userRepository.existsUserByEmailId(user.getId() ,userDto.getEmailId());
        	
        	if(isUserAlreadyExist) {
        		throw new ResourceAlreadyExistException(
						"User with emailId: " + userDto.getEmailId() + " already exist");
        	}else {
        		user.setEmailId(userDto.getEmailId());
        	}
        }
        
        
        if(!CollectionUtils.isEmpty(userDto.getRoles())) {
        	List<String> roleNames = userDto.getRoles().stream().map(RoleDTO :: getName).collect(Collectors.toList());
        	user.setRoles(roleService.findByNameIn(roleNames));
        }
         return userRepository.save(user);
     }

	@Override
	public User findById(Long id) throws ResourceNotFoundException {
		return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id:" + id));
	}
}
