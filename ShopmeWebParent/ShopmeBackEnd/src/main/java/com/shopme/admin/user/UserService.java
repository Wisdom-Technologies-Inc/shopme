package com.shopme.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopme.common.entities.Role;
import com.shopme.common.entities.User;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<User> listAll(){
		return userRepository.findAll();
	}
	
	public List<Role> listRoles(){
		return roleRepository.findAll();
	}
	
	public void save(User user) {
		encodePassword(user);
		userRepository.save(user);
	}
	
	public void encodePassword(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
	}
	
	public boolean isEmailunique(String email) {
		User user = userRepository.getUserByEmail(email);
		return user == null;
	}
}
