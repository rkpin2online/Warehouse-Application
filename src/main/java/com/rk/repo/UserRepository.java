package com.rk.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.rk.model.User;

public interface UserRepository 
	extends	JpaRepository<User, Integer> 
{
	
	Optional<User> findByEmail(String email);

	@Modifying
	@Query("UPDATE User SET active=:status WHERE id=:id")
	public int updateUserStatus(int status, Integer id);
}
