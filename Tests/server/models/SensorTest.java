package server.models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import server.models.categories.SensorCategory;
import server.models.environmentVariable.DiscreteValue;
import server.models.environmentVariable.EnvironmentVariable;

class SensorTest {

	private Sensor sensor;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		
	}

	@BeforeEach
	void setUp() throws Exception {
		SensorCategory category = new SensorCategory(1,"Category","Description Category");
		EnvironmentVariable variable = new EnvironmentVariable(1,"Variable","Description Variable","N/A",new DiscreteValue(0,new ArrayList<String>(Arrays.asList("True","False")),"False"));
		sensor = new Sensor(1, "Sensor", "Description Sensor", variable);
		sensor.setSensorCategory(category);
	}

	@AfterEach
	void tearDown() throws Exception {
		
	}

	@Test
	void testChangeValue() {
		sensor.changeValue("True");
		assertTrue(sensor.getEnvironmentVariables().getCurrentValue().equals("True"));
	}
	
	@Test
	void testSetEnvironmentVariable() {
		EnvironmentVariable variable = new EnvironmentVariable("New Variable","New Description","N/A",new DiscreteValue(0,new ArrayList<String>(Arrays.asList("On","Off")),"Off"));
		sensor.setEnvironmentVariable(variable);
		assertTrue(sensor.getEnvironmentVariables() == variable);
	}
	
	@Test
	void testSetId() {
		sensor.setId(2);
		assertTrue(sensor.getId() == 2);
	}
	
	@Test
	void testSetSensorCategory() {
		SensorCategory category = new SensorCategory();
		sensor.setSensorCategory(category);
		assertTrue(sensor.getSensorCategory() == category);
	}
	
	@Test
	void testToJson() {
		JSONObject sensor = this.sensor.toJson();
		JSONObject category = sensor.getJSONObject("category");
		JSONObject environmentVariable = sensor.getJSONObject("environmentVariable");
		JSONObject value = environmentVariable.getJSONObject("value");
		try {
			assertTrue(sensor.getInt("id") == 1);
			assertTrue(sensor.getString("name").equals("Sensor"));
			assertTrue(sensor.getString("description").equals("Description Sensor"));
			
			assertTrue(category.getInt("id") == 1);
			assertTrue(category.getString("name").equals("Category"));
			assertTrue(category.getString("description").equals("Description Category"));
			
			assertTrue(environmentVariable.getInt("id") == 1);
			assertTrue(environmentVariable.getString("name").equals("Variable"));
			assertTrue(environmentVariable.getString("description").equals("Description Variable"));
			assertTrue(environmentVariable.getString("unit").equals("N/A"));

			
			assertTrue(value.getString("type").equals("discrete"));
			assertTrue(value.getString("currentValue").equals("False"));
			ArrayList<String> possibleValue = ((DiscreteValue)this.sensor.getEnvironmentVariables().getValue()).getPossibleValues();
			for (int i = 0; i < value.getJSONArray("possibleValues").length(); i++) {
				assertTrue(value.getJSONArray("possibleValues").get(i).equals(possibleValue.get(i)));
			}
		}catch (Exception e) {
			fail(e.getMessage());
		}
	}
	

	

}
