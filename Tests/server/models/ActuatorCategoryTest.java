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

import server.models.categories.ActuatorCategory;

public class ActuatorCategoryTest {

	public static ActuatorCategory actuatorCategory;
	static ArrayList<Actuator> actuators = new ArrayList<Actuator>();
	static int id = 1;
	static String name = "actuatorCategory";
	static String description = "description";
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		ActuatorCategoryTest.actuatorCategory = new ActuatorCategory(id, name, description, actuators);
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
		actuators.add(new Actuator());
		actuators.add(new Actuator());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		actuatorCategory.setId(id);
		actuatorCategory.setName(name);
		actuatorCategory.setDescription(description);
		actuatorCategory.setActuators(actuators);
		actuators.clear();
	}

	/**
	 * Test method for {@link server.models.ActuatorCategory#getId()}.
	 */
	@Test
	void testGetId() {
		assertTrue(actuatorCategory.getId() == 1);
	}

	/**
	 * Test method for {@link server.models.ActuatorCategory#setId(int)}.
	 */
	@Test
	void testSetId() {
		actuatorCategory.setId(2);
		assertTrue(actuatorCategory.getId() == 2);
	}

	/**
	 * Test method for {@link server.models.ActuatorCategory#setName(java.lang.String)}.
	 */
	@Test
	void testSetName() {
		actuatorCategory.setName("actuatorCategoryModified");
		assertTrue(actuatorCategory.getName().equals("actuatorCategoryModified"));
	}

	/**
	 * Test method for {@link server.models.ActuatorCategory#getName()}.
	 */
	@Test
	void testGetName() {
		assertTrue(actuatorCategory.getName().equals("actuatorCategory"));
	}
	
	/**
	 * Test method for {@link server.models.ActuatorCategory#setDescription(java.lang.String)}.
	 */
	@Test
	void testSetDescription() {
		actuatorCategory.setDescription("actuatorCategoryDescriptionModified");
		assertTrue(actuatorCategory.getDescription().equals("actuatorCategoryDescriptionModified"));
	}

	/**
	 * Test method for {@link server.models.ActuatorCategory#getDescription()}.
	 */
	@Test
	void testGetDescription() {
		assertTrue(actuatorCategory.getDescription().equals("description"));
	}

	/**
	 * Test method for {@link server.models.ActuatorCategory#setActuators(java.util.List)}.
	 */
	@Test
	void testSetActuators() {
		ArrayList<Actuator> actuatorCategoryTest = new ArrayList<Actuator>();
		actuatorCategoryTest.add(new Actuator());
		actuatorCategoryTest.add(new Actuator());
		actuatorCategory.setActuators(actuatorCategoryTest);
		for(int i = 0; i < actuatorCategory.getActuators().size(); i++) {
			assertTrue(actuatorCategory.getActuators().get(i) == actuatorCategoryTest.get(i));
		}
		
	}

	/**
	 * Test method for {@link server.models.ActuatorCategory#getActuators()}.
	 */
	@Test
	void testGetActuators() {
		assertTrue(actuatorCategory.getActuators().get(0) == actuators.get(0));
	}

	

	
}
