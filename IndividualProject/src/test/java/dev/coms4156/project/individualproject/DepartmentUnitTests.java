package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests for the Department class.
 */
@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {

  /** Constant for department. */
  private static final String DEPT_CODE = "COMS";
  private static final String DEPT_CHAIR = "Tony";
  private static final int NUM_MAJORS = 300;

  /** Constant for course 1. */
  private static final String COURSE_CODE_1 = "1";
  private static final int COURSE_CAPACITY_1 = 250;
  private static final String INSTRUCTOR_NAME_1 = "Griffin Newbold";
  private static final String LOCATION_1 = "417 IAB";
  private static final String TIME_SLOT_1 = "11:40-12:55";

  /** Constant for course 2. */
  private static final String COURSE_CODE_2 = "2";
  private static final int COURSE_CAPACITY_2 = 200;
  private static final String INSTRUCTOR_NAME_2 = "Mike";
  private static final String LOCATION_2 = "301 Uris";
  private static final String TIME_SLOT_2 = "8:40-9:55";

  /** The test department instance used for testing. */
  public static Department testDepartment;
  private static Course testCourse1;

  /**
   * Sets up a test department instance before all tests.
   */
  @BeforeAll
  public static void setupDepartmentForTesting() {
    HashMap<String, Course> courses = new HashMap<>();
    testCourse1 = new Course(INSTRUCTOR_NAME_1, LOCATION_1, TIME_SLOT_1, COURSE_CAPACITY_1);
    courses.put(COURSE_CODE_1, testCourse1);
    testDepartment = new Department(DEPT_CODE, courses, DEPT_CHAIR, NUM_MAJORS);
  }

  /**
   * Tests the getDepartmentChair method.
   */
  @Test
  public void getDepartmentChairTest() {
    assertEquals(DEPT_CHAIR, testDepartment.getDepartmentChair());
  }

  /**
   * Tests adding a person to the number of majors.
   */
  @Test
  public void addPersonToMajorTest() {
    testDepartment.addPersonToMajor();
    assertEquals(NUM_MAJORS+1, testDepartment.getNumberOfMajors(),
        "Number of majors should increase by 1.");
  }

  /**
   * Tests the case when dropping a person from major.
   */
  @Test
  public void dropPersonFromMajorNoMajorsTest() {
    // First, reduce the number of majors to 0
    for (int i = 0; i < NUM_MAJORS+1; i++) {
      testDepartment.dropPersonFromMajor();
    }
    assertEquals(0, testDepartment.getNumberOfMajors(),
        "Number of majors should be 0.");

    // Try to drop below 0 and ensure it doesn't go negative
    testDepartment.dropPersonFromMajor();
    assertEquals(0, testDepartment.getNumberOfMajors(),
        "Number of majors should not go below 0.");
  }

  /**
   * Tests adding a course to the department.
   */
  @Test
  public void addCourseTest() {
    Course newCourse = new Course(INSTRUCTOR_NAME_2, LOCATION_2, TIME_SLOT_2, COURSE_CAPACITY_2);
    testDepartment.addCourse(COURSE_CODE_2, newCourse);
    assertTrue(testDepartment.getCourseSelection().containsKey(COURSE_CODE_2));
  }

  /**
   * Tests creating and adding a new course.
   */
  @Test
  public void createCourseTest() {
    testDepartment.createCourse("3", "Hanah", "633 MUDD", "14:00-15:15", 150);
    assertTrue(testDepartment.getCourseSelection().containsKey("3"));
    Course currentCourse = testDepartment.getCourseSelection().get("3");
    assertEquals("Hanah", currentCourse.getInstructorName());
    assertEquals("633 MUDD", currentCourse.getCourseLocation());
    assertEquals("14:00-15:15", currentCourse.getCourseTimeSlot());
  }

  /**
   * Tests the toString method of the Department class.
   */
  @Test
  public void toStringTest() {
    String expectedOutput = DEPT_CODE + " 1: \nInstructor: " + INSTRUCTOR_NAME_1 + "; Location: "
        + LOCATION_1 + "; Time: " + TIME_SLOT_1 +"\n"
        + DEPT_CODE + " 2: \nInstructor: " + INSTRUCTOR_NAME_2 + "; Location: "
        + LOCATION_2 + "; Time: " + TIME_SLOT_2 +"\n"
        + DEPT_CODE + " 3: \nInstructor: Hanah; Location: 633 MUDD; Time: 14:00-15:15\n";
    assertEquals(expectedOutput, testDepartment.toString(),
        "toString should return correct department and course details.");
  }
}

