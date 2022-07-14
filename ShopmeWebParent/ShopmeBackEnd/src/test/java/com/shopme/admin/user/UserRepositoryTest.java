package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.attribute.UserPrincipal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entities.Role;
import com.shopme.common.entities.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateNewUserWithOneRole() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User user = new User("name@wisdomtech.com", "jakanneh", "Julius", "Kanneh");
		user.addRole(roleAdmin);
		
		User savedUser = userRepository.save(user);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateNewUserWithTwoRoles() {
		
		User userPrince = new User("prince@wisdomtech.com", "princeK", "Prince", "Kanneh");
		Role roleAssistant = new Role(5);
		Role roleEditor = new Role(3);
		
		userPrince.addRole(roleAssistant);
		userPrince.addRole(roleEditor);
		
		User savedUser = userRepository.save(userPrince);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllUsers() {
		List<User> users = userRepository.findAll();
		users.forEach(user -> System.out.println(user));
	}
	
	@Test
	public void testGetUserById() {
		User user = userRepository.findById(1).get();
		System.out.println(user);
		assertThat(user).isNotNull();
	}
	
	@Test
	public void updateUserDetails() {
		User user = userRepository.findById(1).get();
		user.setEmail("newemail@wisdomtech.com");
		User updatedUser = userRepository.save(user);
		System.out.println(updatedUser);
		
		assertThat(updatedUser).isNotNull();
	}
	
	@Test
	public void testUpdateUserRoles() {
		User userPrince = userRepository.findById(2).get();
		Role roleEditor = new Role(2);
		Role roleSalesPerson = new Role(3);
		
		userPrince.getRoles().remove(roleEditor);
		userPrince.getRoles().add(roleSalesPerson);
		
		assertThat(userRepository.save(userPrince)).isNotNull();
	}
	
	@Test
	public void testDeleteUser() {
		userRepository.deleteById(2);
	}
	
	@Test
	public void testGetUserByEmail() {
		String email = "tim@wisdomtech.com";
		User user = userRepository.getUserByEmail(email);
		assertThat(user != null).isTrue();
	}
	
	@Test
	public void testCountById() {
		Integer id = 1;
		assertThat(userRepository.countById(id)).isGreaterThan(0);
	}
	
	@Test
	public void testDisableUer() {
		int id = 20;
		userRepository.updateEnabledStatus(id, false);
	}
	
	@Test
	public void testEnableUer() {
		int id = 20;
		userRepository.updateEnabledStatus(id, true);
	}
	
	@Test
	public void updateUserEnabled(Integer id, boolean eanbled) {
		userRepository.updateEnabledStatus(id, eanbled);
	}
}
