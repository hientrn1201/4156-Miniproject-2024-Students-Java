package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

  /**
   * Tests the saveContentsToFile method.
   */
  @Test
  void testSaveContentsToFile() throws IOException {
    String testFilePath = "./test_data.txt";
    MyFileDatabase myFileDatabaseClone = new MyFileDatabase(1, testFilePath);
    myFileDatabaseClone.setMapping(myFileDatabase.getDepartmentMapping());

    myFileDatabaseClone.saveContentsToFile();

    File savedFile = new File(testFilePath);
    assertTrue(savedFile.exists(), "File should be created");

    String fileContentClone = new String(Files.readAllBytes(Paths.get(testFilePath)));
    String fileContent = new String(Files.readAllBytes(Paths.get("./data.txt")));
    assertEquals(fileContent, fileContentClone);

    // Clean up: delete the test file
    assertTrue(savedFile.delete());
  }
}