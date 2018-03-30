package server.models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class RoleTest {

	public static Role role;
	static ArrayList<Right> rights = new ArrayList<Right>();
	static int id = 1;
	static String name = "name"; 
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		RoleTest.role = new Role(id, name, rights);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		rights.add(new Right());
		rights.add(new Right());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		role.setId(id);
		role.setName(name);
		role.setRights(rights);
		rights.clear();
	}

	/**
	 * Test method for {@link server.models.Role#getId()}.
	 */
	@Test
	void testGetId() {
		assertTrue(role.getId() == 1);
	}

	/**
	 * Test method for {@link server.models.Role#setId(int)}.
	 */
	@Test
	void testSetId() {
		role.setId(2);
		assertTrue(role.getId() == 2);
	}

	/**
	 * Test method for {@link server.models.Role#setName(java.lang.String)}.
	 */
	@Test
	void testSetName() {
		role.setName("rightModified");
		assertTrue(role.getName().equals("rightModified"));
	}

	/**
	 * Test method for {@link server.models.Role#getName()}.
	 */
	@Test
	void testGetName() {
		assertTrue(role.getName().equals("name"));
	}

	/**
	 * Test method for {@link server.models.Role#setRights(java.util.List)}.
	 */
	@Test
	void testSetRights() {
		ArrayList<Right> roleTest = new ArrayList<Right>();
		roleTest.add(new Right());
		roleTest.add(new Right());
		role.setRights(roleTest);
		for(int i = 0; i < role.getRights().size(); i++) {
			assertTrue(role.getRights().get(i) == roleTest.get(i));
		}
		
	}

	/**
	 * Test method for {@link server.models.Role#getRights()}.
	 */
	@Test
	void testGetCommands() {
		assertTrue(role.getRights().get(0) == rights.get(0));
	}

	

	
}
