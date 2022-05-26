package com.appsdeveloperblog.photoapp.api.users.service;

import java.util.ArrayList;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.appsdeveloperblog.photoapp.api.users.data.UserRepository;
import com.appsdeveloperblog.photoapp.api.users.data.UserEntity.UserEntity;
import com.appsdeveloperblog.photoapp.api.users.shared.UserDto;


@Service
public class UsersServiceImpl implements UsersService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public UserDto createUser(UserDto userDetails) {
		userDetails.setUserId(UUID.randomUUID().toString());
		String password=bCryptPasswordEncoder.encode(userDetails.getPassword());
		
		userDetails.setPassword(password);
		
		UserEntity userEntity = new UserEntity();
		userEntity.setUserId(userDetails.getUserId());
		userEntity.setFirstName(userDetails.getFirstName());
		userEntity.setLastName(userDetails.getLastName());
		userEntity.setFirstName(userDetails.getFirstName());
		userEntity.setLastName(userDetails.getLastName());
		userEntity.setEmail(userDetails.getEmail());
		userEntity.setEncryptedPassword(password);
		userRepository.save(userEntity);
		
		//UserDto returnvalue = modelMapper.map(userEntity, UserDto.class);
		return userDetails;
	}

	@Override
	public UserDto getUserDetailsByEmail(String email) throws UsernameNotFoundException{
		UserEntity userEntity = userRepository.findByEmail(email);
		
		if(userEntity==null) {
			throw new UsernameNotFoundException(email);
		}
		
		return new ModelMapper().map(userEntity, UserDto.class);
	}

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		System.out.println(username);
		UserEntity userEntity = userRepository.findByEmail(username);
		
		if(userEntity ==null) {
			throw new UsernameNotFoundException(username);
		}
		
		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
	
	}


	

	
}
