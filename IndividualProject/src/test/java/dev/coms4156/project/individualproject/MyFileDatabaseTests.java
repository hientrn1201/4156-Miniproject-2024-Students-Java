package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MyFileDatabaseTests {

  public MyFileDatabase myFileDatabase;

  /**
   * Initialize myFileDatabase test instance before each test.
   */
  @BeforeEach
  void setUp() {
    myFileDatabase = new MyFileDatabase(0, "./data.txt");
  }

  /**
   * Cleans up after each test.
   */
  @AfterEach
  void tearDown() {
    myFileDatabase = null;
  }

  /**
   * Tests the toString method.
   */
  @Test
  void testToString() {
    String result = myFileDatabase.toString();
    assertTrue(result.contains("COMS"));
    assertTrue(result.contains("IEOR"));
    assertTrue(result.contains("PSYC"));
  }
}