package dev.coms4156.project.individualproject;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a course within an educational institution.
 * This class stores information about the course, including its instructor name,
 * location, time slot, and capacity.
 */
public class Course implements Serializable {

  /**
   * Constructs a new Course object with the given parameters. Initial count starts at 0.
   *
   * @param instructorName     The name of the instructor teaching the course.
   * @param courseLocation     The location where the course is held.
   * @param timeSlot           The time slot of the course.
   * @param capacity           The maximum number of students that can enroll in the course.
   */
  public Course(String instructorName, String courseLocation, String timeSlot, int capacity) {
    this.courseLocation = courseLocation;
    this.instructorName = instructorName;
    this.courseTimeSlot = timeSlot;
    this.enrollmentCapacity = capacity;
    this.enrolledStudentCount = 0;
  }

  /**
   * Enrolls a student in the course if there is space available.
   *
   * @return true if the student is successfully enrolled, false otherwise.
   */
  public boolean enrollStudent() {
    if (enrolledStudentCount < enrollmentCapacity) {
      enrolledStudentCount++;
      return true;
    }
    return false;
  }

  /**
   * Drops a student from the course if a student is enrolled.
   *
   * @return true if the student is successfully dropped, false otherwise.
   */
  public boolean dropStudent() {
    if (enrolledStudentCount == 0) {
      return false;
    }
    enrolledStudentCount--;
    return true;
  }

  /**
   * Retrieves the location where the course is held.
   *
   * @return The String representing the course location
   */
  public String getCourseLocation() {
    return this.courseLocation;
  }

  /**
   * Retrieves the instructor name of the course.
   *
   * @return The String representing the course instructor name
   */
  public String getInstructorName() {
    return this.instructorName;
  }

  /**
   * Retrieves the time slot when the course is held.
   *
   * @return The String representing the course time slot
   */
  public String getCourseTimeSlot() {
    return this.courseTimeSlot;
  }

  /**
   * Returns a string representation of the course, including the instructor's name,
   * location, and time slot.
   *
   * @return A string describing the course.
   */
  public String toString() {
    return "\nInstructor: " + instructorName + "; Location: " + courseLocation + "; Time: "
        + courseTimeSlot;
  }

  /**
   * Reassigns the instructor of the course to a new instructor.
   *
   * @param newInstructorName The name of the new instructor.
   */
  public void reassignInstructor(String newInstructorName) {
    this.instructorName = newInstructorName;
  }

  /**
   * Reassigns the location of the course to a new location.
   *
   * @param newLocation The new location for the course.
   */
  public void reassignLocation(String newLocation) {
    this.courseLocation = newLocation;
  }

  /**
   * Reassigns the time slot of the course to a new time.
   *
   * @param newTime The new time slot for the course.
   */
  public void reassignTime(String newTime) {
    this.courseTimeSlot = newTime;
  }

  /**
   * Sets the enrolled student count for the course.
   *
   * @param count The new count of enrolled students.
   */
  public void setEnrolledStudentCount(int count) {
    this.enrolledStudentCount = count;
  }

  /**
   * Checks if the course has reached its maximum enrollment capacity.
   *
   * @return true if the course is full, false otherwise.
   */
  public boolean isCourseFull() {
    return enrollmentCapacity <= enrolledStudentCount;
  }

  @Serial
  private static final long serialVersionUID = 123456L;
  private final int enrollmentCapacity;
  private int enrolledStudentCount;
  private String courseLocation;
  private String instructorName;
  private String courseTimeSlot;
}
