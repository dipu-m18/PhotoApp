package com.appsdeveloperblog.photoapp.api.users.ui.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appsdeveloperblog.photoapp.api.users.service.UsersService;
import com.appsdeveloperblog.photoapp.api.users.shared.UserDto;
import com.appsdeveloperblog.photoapp.api.users.ui.models.CreateUserRequestModel;
import com.appsdeveloperblog.photoapp.api.users.ui.models.CreateUserResponseModel;

@RestController
@RequestMapping("/users")
public class UsersController {
	
	@Autowired
	private Environment env;
	
	@Autowired
	UsersService userService;
	
	@GetMapping("/status/check")
	public String status() {
		return "Working on port "+env.getProperty("local.server.port");
	}

	
	@PostMapping(
			)
	public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userDetails) {
		//converting userDetails to userDto
		UserDto userDto=new UserDto();
		userDto.setFirstName(userDetails.getFirstName());
		userDto.setLastName(userDetails.getLastName());
		userDto.setEmail(userDetails.getEmail());
		userDto.setPassword(userDetails.getPassword());
		
		UserDto createdUser=userService.createUser(userDto);
		
		//return value
		CreateUserResponseModel returnValue=new CreateUserResponseModel();
		returnValue.setFirstName(createdUser.getFirstName());
		returnValue.setLastName(createdUser.getLastName());
		returnValue.setEmail(createdUser.getEmail());
		returnValue.setUserId(createdUser.getUserId());
		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
	}
}
