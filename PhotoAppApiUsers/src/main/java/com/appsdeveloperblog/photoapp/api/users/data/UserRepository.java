package com.appsdeveloperblog.photoapp.api.users.data;

import org.springframework.data.repository.CrudRepository;

import com.appsdeveloperblog.photoapp.api.users.data.UserEntity.UserEntity;
import com.appsdeveloperblog.photoapp.api.users.shared.UserDto;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
	UserEntity findByEmail(String email);
	UserDto getUserDetailsByEmail(String email);
}
