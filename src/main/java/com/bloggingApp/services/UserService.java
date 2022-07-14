package com.bloggingApp.services;


import com.bloggingApp.payloads.UserDto;
import com.bloggingApp.payloads.UserResponse;

public interface UserService {

	
	UserDto registerNewUser(UserDto user);
	
	UserDto createUser(UserDto user);
	
	UserDto updateUser(UserDto user, Integer userId);
	
	UserDto getUserById(Integer userId);
	
	UserResponse getAllUsers(Integer pageNumber, Integer pageSize);
	
	void deleteUser(Integer userId);
}
