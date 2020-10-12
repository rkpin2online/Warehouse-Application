package com.rk.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rk.model.User;
import com.rk.repo.UserRepository;
import com.rk.service.IUserService;

@Service
public class UserServiceImpl implements IUserService,UserDetailsService {
	@Autowired
	private UserRepository repo; //HAS-A
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Override
	public Integer saveUser(User user) {
		String pwd = user.getPassword();
		String encPwd = encoder.encode(pwd);
		user.setPassword(encPwd);
		user = repo.save(user);
		return user.getId();
	}
	
	@Override
	public List<User> getAllUsers() {
		return repo.findAll();
	}
	
	@Override
	@Transactional
	public int updateUserStatus(int status, Integer id) {
		return repo.updateUserStatus(status, id);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) 
			throws UsernameNotFoundException {
		Optional<User> opt = repo.findByEmail(username);
		if(opt.isEmpty() || opt.get().getActive()==0)
			throw new UsernameNotFoundException("User Not found");
			
		
		User user = opt.get();
		
		return new org.springframework.security.core.userdetails.User(
				username, 
				user.getPassword(), 
				user.getRoles()
				.stream()
				.map(role->new SimpleGrantedAuthority(role))
				.collect(Collectors.toSet())
				);
	}
	
	@Override
	public Optional<User> findByEmail(String email) {
		return repo.findByEmail(email);
	}
}
