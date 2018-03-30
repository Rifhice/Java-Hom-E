package server.models;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import server.models.Role;
import server.models.Right;

public class UserTest {
	
	private User user;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		
	}
	
	@BeforeEach
	void setUp() throws Exception {
		Role role = new Role(2,"nomRole");
		ArrayList<Right> rights = new ArrayList();
		Right right1 = new Right(1, "rightTest1","you can do right 1");
		rights.add(right1);
		user = new User(1, "userTest", "passwordTest", role);
		
	}

	@AfterEach
	void tearDown() throws Exception {
		
	}
	
	@Test
	void testSetPseudo() {
		user.setPseudo("newPseudo");
		assertTrue(user.getPseudo().equals("newPseudo"));
	}
	
	@Test
	void testSetPassword() {
		user.setPassword("newPassword");
		assertTrue(user.getPassword().equals("newPassword"));
	}
	
	@Test
	void testGetRole() {
		assertTrue(user.getRole().getId() == 1);
	}

}
