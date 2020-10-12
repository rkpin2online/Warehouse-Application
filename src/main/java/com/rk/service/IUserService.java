package com.rk.service;

import java.util.List;
import java.util.Optional;

import com.rk.model.User;

public interface IUserService {

	Integer saveUser(User user);
	List<User> getAllUsers();
	Optional<User> findByEmail(String email);
	int updateUserStatus(int status, Integer id);
}
