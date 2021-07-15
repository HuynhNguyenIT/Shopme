package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE) //run test with real database
@Rollback(false)
public class UerRepositoryTests {
	@Autowired
	private UserRepository repo;
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateTable() {
		
	}
	
	@Test
	public void testCreateUserWithOneRole() {
		User userNamHM = new User("huynhktpm2@gmail.com", "huynh2020", "Nguyen", "Huynh");
		//Role roleAdmin = entityManager.find(Role.class, 1); 
		Role roleAdmin = new Role(1);
		userNamHM.addRole(roleAdmin);
		User savedUser= repo.save(userNamHM);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateUserWithManyRole() {
		User userRavi = new User("ravi@gmail.com", "ravi2020", "Ravi", "Kumar");
		Role roleEditor= new Role(3);
		Role roleAssistant= new Role(5);
		
		userRavi.addRole(roleEditor);
		userRavi.addRole(roleAssistant);
		User savedUser= repo.save(userRavi);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testLListAllUser() {
		Iterable<User> listUser= repo.findAll();
		listUser.forEach(user ->System.out.print(user));
	}
	
	@Test
	public void testGetUserById() {
		User userNam = repo.findById(2).get();
		System.out.println(userNam);
		assertThat(userNam).isNotNull();
	}
	
	@Test
	public void testUpdateUserDetails() {
		User userNam = repo.findById(2).get();
		userNam.setEnables(true);
		userNam.setEmail("namjavaprogrammer@gmail.com");
		
		repo.save(userNam);
	}
	
	@Test
	public void testUpdateUserRoles() {
		User userRavi = repo.findById(5).get();
		Role roleEditor= new Role(3);
		Role roleSalesperson= new Role(2);
		userRavi.getRoles().remove(roleEditor);
		userRavi.getRoles().add(roleSalesperson);
		repo.save(userRavi);
		
	}
	
	@Test
	public void testDeleteUser() {
		Integer userId=6;
		repo.deleteById(userId);
	}
	
	@Test
	public void testGetUserByEmail() {
		String email = "ravi@gmail.com";
		//User user = repo.getUserByEmail(email);
		User user= repo.getUserByEmail(email);
		assertThat(user).isNotNull();
	
	}@Test
	public void testCountById() {
		Integer id = 2;
		long dem = repo.countById(id);
		assertThat(dem).isGreaterThan(0);
	}
	
	
	
	
}
