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

import server.models.categories.SensorCategory;

public class SensorCategoryTest {
  
  public static SensorCategory sensorCategory;
  static ArrayList<Sensor> sensors = new ArrayList<Sensor>();
  static int id = 1;
  static String name = "sensorCategory";
  static String description = "description";
  
  
  /**
   * @throws java.lang.Exception
   */
  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    SensorCategoryTest.sensorCategory = new SensorCategory(id, name, description, sensors);

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
 
    sensors.add(new Sensor());
 
    sensors.add(new Sensor());
 
  }
 

 
  /**
 
   * @throws java.lang.Exception
 
   */
 
  @AfterEach
 
  void tearDown() throws Exception {
 
    sensorCategory.setId(id);
 
    sensorCategory.setName(name);
 
    sensorCategory.setDescription(description);
 
    sensorCategory.setSensors(sensors);
 
    sensors.clear();
 
  }
 

/**
 
   * Test method for {@link server.models.SensorCategory#getId()}.
 
   */
 
  @Test
 
  void testGetId() {
 
    assertTrue(sensorCategory.getId() == 1);
 
  }
 

 
  /**
 
   * Test method for {@link server.models.SensorCategory#setId(int)}.
 
   */
 
  @Test
 
  void testSetId() {
 
    sensorCategory.setId(2);
 
    assertTrue(sensorCategory.getId() == 2);
 
  }
 

 
  /**
 
   * Test method for {@link server.models.SensorCategory#setName(java.lang.String)}.
 
   */
 
  @Test
 
  void testSetName() {
 
    sensorCategory.setName("sensorCategoryModified");
 
    assertTrue(sensorCategory.getName().equals("sensorCategoryModified"));
 
  }
 

 
  /**
 
   * Test method for {@link server.models.SensorCategory#getName()}.
 
   */
 
@Test
 
  void testGetName() {
 
    assertTrue(sensorCategory.getName().equals("sensorCategory"));
 
  }
 
  
 
  /**
 
   * Test method for {@link server.models.SensorCategory#setDescription(java.lang.String)}.
 
   */
 
  @Test
 
  void testSetDescription() {
 
    sensorCategory.setDescription("sensorCategoryDescriptionModified");
 
    assertTrue(sensorCategory.getDescription().equals("sensorCategoryDescriptionModified"));
 
  }
 

 
  /**
 
   * Test method for {@link server.models.SensorCategory#getDescription()}.
 
   */
 
  @Test
 
  void testGetDescription() {
 
    assertTrue(sensorCategory.getDescription().equals("description"));
 
  }
 
/**
 
   * Test method for {@link server.models.SensorCategory#setSensors(java.util.List)}.
 
   */
 
  @Test
 
  void testSetActuators() {
 
    ArrayList<Sensor> sensorCategoryTest = new ArrayList<Sensor>();
 
    sensorCategoryTest.add(new Sensor());
 
    sensorCategoryTest.add(new Sensor());
 
    sensorCategory.setSensors(sensorCategoryTest);;
 
    for(int i = 0; i < sensorCategory.getSensors().size(); i++) {
 
      assertTrue(sensorCategory.getSensors().get(i) == sensorCategoryTest.get(i));
 
    }
 
    
 
  }
 

 
  /**
 
   * Test method for {@link server.models.SensorCategory#getSensors()}.
 
   */
 
  @Test
 
  void testGetActuators() {
 
    assertTrue(sensorCategory.getSensors().get(0) == sensors.get(0));
 
  }
 

 
 

 
}
 