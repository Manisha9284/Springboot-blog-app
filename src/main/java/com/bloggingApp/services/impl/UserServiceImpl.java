package com.bloggingApp.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bloggingApp.config.AppConstants;
import com.bloggingApp.entities.Role;
import com.bloggingApp.entities.User;
import com.bloggingApp.payloads.UserDto;
import com.bloggingApp.payloads.UserResponse;
import com.bloggingApp.repositories.RoleRepo;
import com.bloggingApp.repositories.UserRepo;
import com.bloggingApp.services.UserService;
import com.bloggingApp.exceptions.ResourceNotFoundException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	
	//***** CREATE USER *****
	
	@Override
	public UserDto createUser(UserDto userDto) {
		
		User user = this.DtoToUser(userDto);
		
		User savedUser = this.userRepo.save(user);
		
		return this.UserToDto(savedUser);
	}
	
	
	//***** UPDATE USER *****
	
	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		
		User user = this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		
		User upadteUser = this.userRepo.save(user);
		UserDto userDto1 = this.UserToDto(upadteUser);
		
		return userDto1;
	}

	
	
	//***** GET USER BY ID *****
	@Override
	public UserDto getUserById(Integer userId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		
		return this.UserToDto(user);
	}

	
	//***** GET ALL USER *****
//	@Override
//	public List<UserDto> getAllUsers() {
//		
//		List<User> users = this.userRepo.findAll();
//		
//		//convert user to userDto
//		List<UserDto> userDtos =  users.stream().map(user->this.UserToDto(user)).collect(Collectors.toList());
//		
//		return userDtos;
//	}
	
	@Override
	public UserResponse getAllUsers(Integer pageNumber , Integer pageSize) {
		
		//get page size and page number from params
		Pageable p = PageRequest.of(pageNumber, pageSize);
		
		
		Page<User> pageOfUser = this.userRepo.findAll(p);
		
		List<User> allUsers = pageOfUser.getContent();
		
		
		List<UserDto> postDtos = allUsers.stream().map(user->this.UserToDto(user)).collect(Collectors.toList());
		
		UserResponse userResponse = new UserResponse();
		
		userResponse.setContent(postDtos);
		userResponse.setPageNumber(pageOfUser.getNumber());
		userResponse.setPageSize(pageOfUser.getSize());
		userResponse.setTotalElement(pageOfUser.getTotalElements());
		
		userResponse.setTotalPages(pageOfUser.getTotalPages());
		
		userResponse.setLastPage(pageOfUser.isLast());
		
		return userResponse;
	}
		

	
	//***** DELETE USER *****
	@Override
	public void deleteUser(Integer userId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", " Id", userId));

		this.userRepo.delete(user);
	}
	
	
	//***** CONVERT DTO TO USER *****
	public User DtoToUser(UserDto userDto) {
		
		User user = this.modelMapper.map(userDto, User.class);
//		User user = new User();
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
		
		return user;
		
	}
	
	
	//***** CONVERT USER  TO DTO*****
	public UserDto UserToDto(User user)
	{
	
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
	
//		UserDto userDto = new UserDto();
//		
//		userDto.setId(userDto.getId());
//		userDto.setName(userDto.getName());
//		userDto.setEmail(userDto.getEmail());
//		userDto.setPassword(userDto.getPassword());
//		userDto.setAbout(userDto.getAbout());
		
		return userDto;
		
	}


	@Override
	public UserDto registerNewUser(UserDto userDto) {


		User user = this.modelMapper.map(userDto, User.class);
		
		//encode the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		
		//roles
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
			
		user.getRoles().add(role);
			
		User newUser = this.userRepo.save(user);
			
		return  this.modelMapper.map(newUser, UserDto.class);
	}

}
