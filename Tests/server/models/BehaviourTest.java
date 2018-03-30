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

import server.models.Behaviour;
import server.models.evaluable.Expression;


public class BehaviourTest {
	
	public static Behaviour behaviour;
	static ArrayList<AtomicAction> atomicActions = new ArrayList<AtomicAction>();
	static ArrayList<ComplexAction> complexActions = new ArrayList<ComplexAction>();

	static int id = 1;
	static String name = "behaviour";
	static String description = "description";
	static boolean isActivated = true;
	static Expression expression = new Expression(); 
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		BehaviourTest.behaviour = new Behaviour(id, name, description, expression, isActivated, atomicActions, complexActions);
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
		atomicActions.add(new AtomicAction());
		atomicActions.add(new AtomicAction());
		complexActions.add(new ComplexAction());
		complexActions.add(new ComplexAction());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		behaviour.setId(id);
		behaviour.setName(name);
		behaviour.setDescription(description);
		behaviour.setExpression(expression);
		behaviour.setAtomicActions(atomicActions);
		behaviour.setComplexActions(complexActions);
		atomicActions.clear();
		complexActions.clear();
	}

	/**
	 * Test method for {@link server.models.Behaviour#getId()}.
	 */
	@Test
	void testGetId() {
		assertTrue(behaviour.getId() == 1);
	}

	/**
	 * Test method for {@link server.models.Behaviour#setId(int)}.
	 */
	@Test
	void testSetId() {
		behaviour.setId(2);
		assertTrue(behaviour.getId() == 2);
	}

	/**
	 * Test method for {@link server.models.Behaviour#setName(java.lang.String)}.
	 */
	@Test
	void testSetName() {
		behaviour.setName("behaviourModified");
		assertTrue(behaviour.getName().equals("behaviourModified"));
	}

	/**
	 * Test method for {@link server.models.Behaviour#getName()}.
	 */
	@Test
	void testGetName() {
		assertTrue(behaviour.getName().equals("behaviour"));
	}
	
	/**
	 * Test method for {@link server.models.Behaviour#setDescription(java.lang.String)}.
	 */
	@Test
	void testSetDescription() {
		behaviour.setDescription("behaviourDescriptionModified");
		assertTrue(behaviour.getDescription().equals("behaviourDescriptionModified"));
	}

	/**
	 * Test method for {@link server.models.Behaviour#getDescription()}.
	 */
	@Test
	void testGetDescription() {
		assertTrue(behaviour.getDescription().equals("description"));
	}
	
	/**
	 * Test method for {@link server.models.Behaviour#setIsActivatedjava.lang.String)}.
	 */
	@Test
	void testSetIsActivated() {
		behaviour.setActivated(true);
		assertTrue(behaviour.isActivated() == true);
	}

	/**
	 * Test method for {@link server.models.Behaviour#getIsActivated()}.
	 */
	@Test
	void testGetIsActivated() {
		assertTrue(behaviour.isActivated() == true);
	}
	
	
	/**
	 * Test method for {@link server.models.Behaviour#setExpression(java.util.List)}.
	 */
	@Test
	void testSetExpression() {
		Expression expressionTest = new Expression();
		behaviour.setExpression(expressionTest);
		assertTrue(behaviour.getExpression() == expressionTest);	
		
	}

	/**
	 * Test method for {@link server.models.Behaviour#getExpression()}.
	 */
	@Test
	void testGetExpression() {
		assertTrue(behaviour.getExpression() == expression);
	}

	/**
	 * Test method for {@link server.models.Behaviour#setAtomicActions(java.util.List)}.
	 */
	@Test
	void testSetAtomicAction() {
		ArrayList<AtomicAction> behaviourTest = new ArrayList<AtomicAction>();
		behaviourTest.add(new AtomicAction());
		behaviourTest.add(new AtomicAction());
		behaviour.setAtomicActions(behaviourTest);
		for(int i = 0; i < behaviour.getAtomicActions().size(); i++) {
			assertTrue(behaviour.getAtomicActions().get(i) == behaviourTest.get(i));
		}
		
	}

	/**
	 * Test method for {@link server.models.Behaviour#getAtomicAction()}.
	 */
	@Test
	void testGetAtomicAction() {
		assertTrue(behaviour.getAtomicActions().get(0) == atomicActions.get(0));
	}
	
	/**
	 * Test method for {@link server.models.Behaviour#setComplexActions(java.util.List)}.
	 */
	@Test
	void testSetComplexAction() {
		ArrayList<ComplexAction> behaviourTest = new ArrayList<ComplexAction>();
		behaviourTest.add(new ComplexAction());
		behaviourTest.add(new ComplexAction());
		behaviour.setComplexActions(behaviourTest);
		for(int i = 0; i < behaviour.getComplexActions().size(); i++) {
			assertTrue(behaviour.getComplexActions().get(i) == behaviourTest.get(i));
		}
		
	}

	/**
	 * Test method for {@link server.models.Behaviour#getComplexAction()}.
	 */
	@Test
	void testGetComplexcAction() {
		assertTrue(behaviour.getComplexActions().get(0) == complexActions.get(0));
	}

	
}
