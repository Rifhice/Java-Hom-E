package server.models;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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
import server.models.environmentVariable.DiscreteValue;
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
		user.setRights(rights);
		
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
		assertTrue(user.getRole().getId() == 2);
	}
	
	@Test
	void testToJson() {
		JSONObject user = this.user.toJson();
		JSONObject role = user.getJSONObject("role");
		JSONArray rights = user.getJSONArray("rights");
		try {
			assertTrue(user.getInt("id") == 1);
			assertTrue(user.getString("pseudo").equals("userTest"));
			assertTrue(user.getString("password").equals("passwordTest"));
			
			assertTrue(role.getInt("id") == 2);
			assertTrue(role.getString("name").equals("nomRole"));
			
			if(rights != null) {
				for (int i = 0; i < rights.length(); i++) {
					int id = rights.getJSONObject(i).getInt("id");
					assertTrue(id == (this.user.getRights().get(i).getId()));
				}
			}
		}catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
