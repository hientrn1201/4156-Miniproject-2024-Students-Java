package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests for the Course class.
 */
@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  /** Constant for course. */
  private static final int COURSE_CAPACITY = 250;
  private static final String INSTRUCTOR_NAME = "Griffin Newbold";
  private static final String LOCATION = "417 IAB";
  private static final String TIME_SLOT = "11:40-12:55";

  /**
   * Sets up a test course instance before all tests.
   */
  @BeforeEach
  public void setupCourseForTesting() {
    testCourse = new Course(INSTRUCTOR_NAME, LOCATION, TIME_SLOT, COURSE_CAPACITY);
  }

  /**
   * Tests the toString method.
   */
  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: " + INSTRUCTOR_NAME + "; Location: " + LOCATION
        + "; Time: " + TIME_SLOT;
    assertEquals(expectedResult, testCourse.toString());
  }

  /**
   * Tests the initial number of enroll student count.
   */
  @Test
  public void enrollStudentCountInitializationTest() {
    boolean result = testCourse.dropStudent();
    assertFalse(result);
  }

  /**
   * Tests reassigning a new instructor.
   */
  @Test
  public void reassignInstructorTest() {
    String newInstructorName = "Mike";
    testCourse.reassignInstructor(newInstructorName);
    assertEquals(newInstructorName, testCourse.getInstructorName());
  }

  /**
   * Tests reassigning a new location.
   */
  @Test
  public void reassignLocationTest() {
    String newLocation = "301 Uris";
    testCourse.reassignLocation(newLocation);
    assertEquals(newLocation, testCourse.getCourseLocation());
  }

  /**
   * Tests reassigning a new time slot.
   */
  @Test
  public void reassignTimeSlotTest() {
    String newTimeSlot = "8:40-9:55";
    testCourse.reassignTime(newTimeSlot);
    assertEquals(newTimeSlot, testCourse.getCourseTimeSlot());
  }

  /**
   * Tests enrolling a student.
   */
  @Test
  public void enrollStudentTest() {
    testCourse.setEnrolledStudentCount(50);
    boolean result = testCourse.enrollStudent();
    assertTrue(result, "Student enrollment should be successful when there is capacity.");
    testCourse.setEnrolledStudentCount(COURSE_CAPACITY);
    result = testCourse.enrollStudent();
    assertFalse(result, "Student enrollment should not be successful when there is not capacity.");
  }

  /**
   * Tests dropping a student.
   */
  @Test
  public void dropStudentTest() {
    testCourse.setEnrolledStudentCount(50);
    boolean result = testCourse.dropStudent();
    assertTrue(result, "Student enrollment should be successful when there is capacity.");
  }

  /**
   * Tests the isCourseFull method.
   */
  @Test
  public void isCourseFullTest() {
    testCourse.setEnrolledStudentCount(COURSE_CAPACITY);
    boolean result = testCourse.isCourseFull();
    assertTrue(result, "The course should be full when the capacity is reached.");
    testCourse.setEnrolledStudentCount(0);
    result = testCourse.isCourseFull();
    assertFalse(result, "The course should not be full when there is capacity.");
  }

  /** The test course instance used for testing. */
  public static Course testCourse;
}

