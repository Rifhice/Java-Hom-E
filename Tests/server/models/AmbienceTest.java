/**
 * 
 */
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

import server.models.environmentVariable.DiscreteValue;

/**
 * @author hugom
 *
 */
class AmbienceTest {

	public static Ambience ambience;
	static ArrayList<Behaviour> behaviours = new ArrayList<Behaviour>();
	static int id = 1;
	static String name = "ambience";
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		AmbienceTest.ambience = new Ambience(id, name, behaviours);
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
		behaviours.add(new Behaviour());
		behaviours.add(new Behaviour());
		behaviours.add(new Behaviour());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		ambience.setId(id);
		ambience.setName(name);
		ambience.setBehaviours(behaviours);
		behaviours.clear();
	}

	/**
	 * Test method for {@link server.models.Ambience#getId()}.
	 */
	@Test
	void testGetId() {
		assertTrue(ambience.getId() == 1);
	}

	/**
	 * Test method for {@link server.models.Ambience#setId(int)}.
	 */
	@Test
	void testSetId() {
		ambience.setId(2);
		assertTrue(ambience.getId() == 2);
	}

	/**
	 * Test method for {@link server.models.Ambience#setName(java.lang.String)}.
	 */
	@Test
	void testSetName() {
		ambience.setName("ambienceModified");
		assertTrue(ambience.getName().equals("ambienceModified"));
	}

	/**
	 * Test method for {@link server.models.Ambience#getName()}.
	 */
	@Test
	void testGetName() {
		assertTrue(ambience.getName().equals("ambience"));
	}

	/**
	 * Test method for {@link server.models.Ambience#setBehaviours(java.util.List)}.
	 */
	@Test
	void testSetBehaviours() {
		ArrayList<Behaviour> behaviourTest = new ArrayList<Behaviour>();
		behaviourTest.add(new Behaviour());
		behaviourTest.add(new Behaviour());
		behaviourTest.add(new Behaviour());
		ambience.setBehaviours(behaviourTest);
		for(int i = 0; i < ambience.getBehaviours().size(); i++) {
			assertTrue(ambience.getBehaviours().get(i) == behaviourTest.get(i));
		}
		
	}

	/**
	 * Test method for {@link server.models.Ambience#getBehaviours()}.
	 */
	@Test
	void testGetBehaviours() {
		assertTrue(ambience.getBehaviours().get(0) == behaviours.get(0));
	}

	/**
	 * Test method for {@link server.models.Ambience#addBehaviour(server.models.Behaviour)}.
	 */
	@Test
	void testAddBehaviour() {
		assertTrue(ambience.getBehaviours().size() == 3);
		ambience.addBehaviour(new Behaviour());
		assertTrue(ambience.getBehaviours().size() == 4);
	}

	/**
	 * Test method for {@link server.models.Ambience#deleteBehaviour(server.models.Behaviour)}.
	 */
	@Test
	void testDeleteBehaviour() {
		assertTrue(ambience.getBehaviours().size() == 3);
		ambience.deleteBehaviour(ambience.getBehaviours().get(ambience.getBehaviours().size()-1));
		assertTrue(ambience.getBehaviours().size() == 2);
	}

	/**
	 * Test method for {@link server.models.Ambience#activateAmbience()}.
	 */
	@Test
	void testActivateAmbience() {
		ambience.activateAmbience();
		for(int i = 0; i < ambience.getBehaviours().size(); i++) {
			assertTrue(ambience.getBehaviours().get(i).isActivated());
		}
	}

	/**
	 * Test method for {@link server.models.Ambience#deactivateAmbience()}.
	 */
	@Test
	void testDeactivateAmbience() {
		ambience.deactivateAmbience();
		for(int i = 0; i < ambience.getBehaviours().size(); i++) {
			assertTrue(!ambience.getBehaviours().get(i).isActivated());
		}
	}

	/**
	 * Test method for {@link server.models.Ambience#toJSON()}.
	 */
	@Test
	void testToJSON() {
		JSONObject ambienceJSON = ambience.toJSON();
		JSONArray behavioursARRAY = ambienceJSON.getJSONArray("behaviours");
		try {
			assertTrue(ambienceJSON.getInt("id") == 1);
			assertTrue(ambienceJSON.getString("name").equals("ambience"));
			
			for(int i = 0; i < behavioursARRAY.length(); i++) {
				JSONObject currentBehaviour = behavioursARRAY.getJSONObject(i);
				
				assertTrue(currentBehaviour.getInt("id") == 0);
			}
		}catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
