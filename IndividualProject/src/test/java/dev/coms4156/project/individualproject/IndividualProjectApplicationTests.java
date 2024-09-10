package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the IndividualProjectApplication class.
 */
class IndividualProjectApplicationTests {

  public IndividualProjectApplication app;

  /**
   * Sets up the test environment before each test method.
   * Initializes the IndividualProjectApplication and overrides the database with a test database.
   */
  @BeforeEach
  void setUp() {
    app = new IndividualProjectApplication();
    IndividualProjectApplication.overrideDatabase(new MyFileDatabase(0, "./data.txt"));
  }

  @AfterEach
  void tearDown() {
    app.onTermination();
  }

  /**
   * Tests the main method of IndividualProjectApplication.
   * Ensures that the myFileDatabase is properly initialized.
   */
  @Test
  void runTest() {
    String[] args = {"setup"};
    app.run(args);
    assertNotNull(IndividualProjectApplication.myFileDatabase);

    args = new String[]{};
    app.run(args);
    assertNotNull(IndividualProjectApplication.myFileDatabase);
  }

  /**
   * Tests the resetDataFile method of IndividualProjectApplication.
   * Verifies that the database is reset and contains the expected default departments.
   */
  @Test
  void resetDataFileTest() {
    app.resetDataFile();
    HashMap<String, Department> mapping = IndividualProjectApplication.myFileDatabase
        .getDepartmentMapping();
    assertNotNull(mapping);
    assertTrue(mapping.containsKey("COMS"));
    assertTrue(mapping.containsKey("ECON"));
  }
}
