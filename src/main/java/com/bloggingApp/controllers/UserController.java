package com.bloggingApp.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bloggingApp.payloads.ApiResponse;
import com.bloggingApp.payloads.PostResponse;
import com.bloggingApp.payloads.UserDto;
import com.bloggingApp.payloads.UserResponse;
import com.bloggingApp.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	
	//POST - create user
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
	{
		UserDto createUserDto = this.userService.createUser(userDto);
		
		return new ResponseEntity<UserDto>(createUserDto, HttpStatus.CREATED);
	}
	
	//PUT - update user
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer userId)
	{
		UserDto updatedUser = this.userService.updateUser(userDto, userId);
		
		return ResponseEntity.ok(updatedUser);
	}
	
	
	//DELETE - delete user
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser( @PathVariable("userId") Integer uid)
	{
		this.userService.deleteUser(uid);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfully", false), HttpStatus.OK);
		
	}
	
	
	//GET - get all users
//	@GetMapping("/")
//	public ResponseEntity<List<UserDto>> getAllUser()
//	{
//		 return ResponseEntity.ok(this.userService.getAllUsers());
//	}
	
	@GetMapping("/")
	public ResponseEntity<UserResponse> getAllPosts(@RequestParam(value = "pageNumber" , defaultValue = "1", required = false) Integer pageNumber,
													 @RequestParam(value = "pageSize" , defaultValue = "5" , required = false) Integer pageSize)
	{
		UserResponse userResponse = this.userService.getAllUsers(pageNumber , pageSize);
		 
		 return new ResponseEntity<UserResponse>(userResponse, HttpStatus.OK);
	}
	
	
	//GET - get single user
		@GetMapping("/{userId}")
		public ResponseEntity<UserDto> getUserById(@PathVariable Integer userId)
		{
			 UserDto user = this.userService.getUserById(userId);
			 
			 return ResponseEntity.ok(user);
		}
		
}
