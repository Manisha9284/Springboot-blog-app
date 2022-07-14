package com.bloggingApp.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.bloggingApp.entities.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
//we are using this class to transfer data
public class UserDto {

	private int id;
	
	@NotEmpty
	@Size(min = 4, message = "Username must be minimum of 4 characters")
	private String name;
	
	@Email(message = "Email address is not valid")
	private String email;
	
	@NotEmpty
	@Size(min = 3, max = 10 ,message = "Password must be min of 3 chars and max of 10 chars")
	//@Pattern(regexp = ) write any pattern from net, if it matches then ok otherwise generate msg
	private String password;
	
	@NotEmpty
	private String about;
	
	private Set<RoleDto> roles= new HashSet<>();
}
