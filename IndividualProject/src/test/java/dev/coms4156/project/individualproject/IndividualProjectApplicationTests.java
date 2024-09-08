package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IndividualProjectApplicationTests {

  IndividualProjectApplication app;

  @BeforeEach
  void setUp() {
    app = new IndividualProjectApplication();
    IndividualProjectApplication.overrideDatabase(new MyFileDatabase(0, "./data.txt"));
  }

  @Test
  void runTest() {
    String[] args = {"setup"};
    app.run(args);
    assertNotNull(IndividualProjectApplication.myFileDatabase);

    args = new String[]{};
    app.run(args);
    assertNotNull(IndividualProjectApplication.myFileDatabase);
  }

  @Test
  void resetDataFileTest() {
    app.resetDataFile();
    assertNotNull(IndividualProjectApplication.myFileDatabase.getDepartmentMapping());
    assertTrue(IndividualProjectApplication.myFileDatabase.getDepartmentMapping().containsKey("COMS"));
    assertTrue(IndividualProjectApplication.myFileDatabase.getDepartmentMapping().containsKey("ECON"));
  }
}
