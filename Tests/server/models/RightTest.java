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


public class RightTest {

	public static Right right;
	static ArrayList<Command> commands = new ArrayList<Command>();
	static int id = 1;
	static String denomination = "denomination"; 
	static String description = "description";
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		RightTest.right = new Right(id, denomination, description, commands);
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
		commands.add(new Command());
		commands.add(new Command());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		right.setId(id);
		right.setDenomination(denomination);
		right.setDescription(description);
		right.setCommands(commands);
		commands.clear();
	}

	/**
	 * Test method for {@link server.models.Right#getId()}.
	 */
	@Test
	void testGetId() {
		assertTrue(right.getId() == 1);
	}

	/**
	 * Test method for {@link server.models.Right#setId(int)}.
	 */
	@Test
	void testSetId() {
		right.setId(2);
		assertTrue(right.getId() == 2);
	}

	/**
	 * Test method for {@link server.models.Right#setDenomination(java.lang.String)}.
	 */
	@Test
	void testSetDenomination() {
		right.setDenomination("rightModified");
		assertTrue(right.getDenomination().equals("rightModified"));
	}

	/**
	 * Test method for {@link server.models.Right#getDenomination()}.
	 */
	@Test
	void testGetDenomination() {
		assertTrue(right.getDenomination().equals("denomination"));
	}
	
	/**
	 * Test method for {@link server.models.Right#setDescription(java.lang.String)}.
	 */
	@Test
	void testSetDescription() {
		right.setDescription("rightDescriptionModified");
		assertTrue(right.getDescription().equals("rightDescriptionModified"));
	}

	/**
	 * Test method for {@link server.models.Right#getDescription()}.
	 */
	@Test
	void testGetDescription() {
		assertTrue(right.getDescription().equals("description"));
	}

	/**
	 * Test method for {@link server.models.Right#setCommands(java.util.List)}.
	 */
	@Test
	void testSetCommands() {
		ArrayList<Command> rightTest = new ArrayList<Command>();
		rightTest.add(new Command());
		rightTest.add(new Command());
		right.setCommands(rightTest);
		for(int i = 0; i < right.getCommands().size(); i++) {
			assertTrue(right.getCommands().get(i) == rightTest.get(i));
		}
		
	}

	/**
	 * Test method for {@link server.models.Right#getCommands()}.
	 */
	@Test
	void testGetCommands() {
		assertTrue(right.getCommands().get(0) == commands.get(0));
	}

	
	/**
	 * Test method for {@link server.models.Right#toJSON()}.
	 */
	@Test
	void testToJSON() {
		JSONObject rightJSON = right.toJson();
		JSONArray commandsARRAY = rightJSON.getJSONArray("commands");
		try {
			assertTrue(rightJSON.getInt("id") == 1);
			assertTrue(rightJSON.getString("denomination").equals("denomination"));
			assertTrue(rightJSON.getString("description").equals("description"));
			
			for(int i = 0; i < commandsARRAY.length(); i++) {
				JSONObject currentCommand = commandsARRAY.getJSONObject(i);
				
				assertTrue(currentCommand.getInt("id") == 0);
			}
		}catch (Exception e) {
			fail(e.getMessage());
		}
		
	}
	
}
