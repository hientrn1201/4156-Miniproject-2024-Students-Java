package dev.coms4156.project.individualproject;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the various endpoints in the RouteController.
 */
@SpringBootTest
@AutoConfigureMockMvc
class RouteControllerTests {

  @Autowired
  private MockMvc mockMvc;

  /**
   * Sets up the test environment before each test.
   * Initializes the test database with sample data.
   */
  @BeforeEach
  void setUp() {
    // Initialize test data
    IndividualProjectApplication.overrideDatabase(new MyFileDatabase(0, "./data.txt"));
  }

  /**
   * Tests the index endpoint.
   *
   * @throws Exception if the test fails
   */
  @Test
  void indexTest() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Welcome")));
  }

  /**
   * Tests the retrieveDepartment endpoint.
   *
   * @throws Exception if the test fails
   */
  @Test
  void retrieveDepartmentTest() throws Exception {
    mockMvc.perform(get("/retrieveDept").param("deptCode", "COMS"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("COMS")));

    mockMvc.perform(get("/retrieveDept").param("deptCode", "NA"))
        .andExpect(status().isNotFound());
  }

  /**
   * Tests the retrieveCourse endpoint.
   *
   * @throws Exception if the test fails
   */
  @Test
  void retrieveCourseTest() throws Exception {
    mockMvc.perform(get("/retrieveCourse")
            .param("deptCode", "COMS")
            .param("courseCode", "1004"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Adam Cannon")))
        .andExpect(content().string(containsString("417 IAB")))
        .andExpect(content().string(containsString("11:40-12:55")));

    mockMvc.perform(get("/retrieveCourse")
            .param("deptCode", "COMS")
            .param("courseCode", "3"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));

    mockMvc.perform(get("/retrieveCourse")
            .param("deptCode", "COM")
            .param("courseCode", "3"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }

  /**
   * Tests the isCourseFull endpoint.
   *
   * @throws Exception if the test fails
   */
  @Test
  void isCourseFullTest() throws Exception {
    mockMvc.perform(get("/isCourseFull")
            .param("deptCode", "COMS")
            .param("courseCode", "1004"))
        .andExpect(status().isOk())
        .andExpect(content().string("false"));

    mockMvc.perform(get("/retrieveCourse")
            .param("deptCode", "COMS")
            .param("courseCode", "3"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  /**
   * Tests the getMajorCountFromDept endpoint.
   *
   * @throws Exception if the test fails
   */
  @Test
  void getMajorCtFromDeptTest() throws Exception {
    mockMvc.perform(get("/getMajorCountFromDept").param("deptCode", "COMS"))
        .andExpect(status().isOk())
        .andExpect(
            content().string("There are: 2700 majors in the department."));

    mockMvc.perform(get("/retrieveDept").param("deptCode", "NA"))
        .andExpect(status().isNotFound());
  }

  /**
   * Tests the identifyDeptChair endpoint.
   *
   * @throws Exception if the test fails
   */
  @Test
  void identifyDeptChairTest() throws Exception {
    mockMvc.perform(get("/idDeptChair").param("deptCode", "COMS"))
        .andExpect(status().isOk())
        .andExpect(
            content().string(containsString("Luca Carloni")));

    mockMvc.perform(get("/retrieveDept").param("deptCode", "NA"))
        .andExpect(status().isNotFound());
  }

  /**
   * Tests the findCourseLocation endpoint.
   *
   * @throws Exception if the test fails
   */
  @Test
  void findCourseLocationTest() throws Exception {
    mockMvc.perform(get("/findCourseLocation")
            .param("deptCode", "COMS")
            .param("courseCode", "1004"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("417 IAB")));

    mockMvc.perform(get("/retrieveCourse")
            .param("deptCode", "COMS")
            .param("courseCode", "3"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  /**
   * Tests the findCourseInstructor endpoint.
   *
   * @throws Exception if the test fails
   */
  @Test
  void findCourseInstructorTest() throws Exception {
    mockMvc.perform(get("/findCourseInstructor")
            .param("deptCode", "COMS")
            .param("courseCode", "1004"))
        .andExpect(status().isOk())
        .andExpect(content().string("Adam Cannon is the instructor for the course."));

    mockMvc.perform(get("/retrieveCourse")
            .param("deptCode", "COMS")
            .param("courseCode", "3"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  /**
   * Tests the findCourseTime endpoint.
   *
   * @throws Exception if the test fails
   */
  @Test
  void findCourseTimeTest() throws Exception {
    mockMvc.perform(get("/findCourseTime")
            .param("deptCode", "COMS")
            .param("courseCode", "1004"))
        .andExpect(status().isOk())
        .andExpect(content().string("The course meets at: 11:40-12:55."));

    mockMvc.perform(get("/retrieveCourse")
            .param("deptCode", "COMS")
            .param("courseCode", "3"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  /**
   * Tests the addMajorToDept endpoint.
   *
   * @throws Exception if the test fails
   */
  @Test
  void addMajorToDeptTest() throws Exception {
    mockMvc.perform(patch("/addMajorToDept").param("deptCode", "COMS"))
        .andExpect(status().isOk())
        .andExpect(content().string("Attribute was updated successfully"));
    mockMvc.perform(get("/getMajorCountFromDept")
            .param("deptCode", "COMS"))
        .andExpect(status().isOk())
        .andExpect(content().string("There are: 2701 majors in the department."));

    mockMvc.perform(patch("/addMajorToDept").param("deptCode", "NA"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }

  /**
   * Tests the removeMajorFromDept endpoint.
   *
   * @throws Exception if the test fails
   */
  @Test
  void removeMajorFromDeptTest() throws Exception {
    mockMvc.perform(patch("/removeMajorFromDept")
            .param("deptCode", "COMS"))
        .andExpect(status().isOk())
        .andExpect(content().string("Attribute was updated or is at minimum"));

    mockMvc.perform(get("/getMajorCountFromDept")
            .param("deptCode", "COMS"))
        .andExpect(status().isOk())
        .andExpect(content().string("There are: 2699 majors in the department."));

    mockMvc.perform(patch("/removeMajorFromDept")
            .param("deptCode", "NA"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }

  /**
   * Tests the dropStudentFromCourse endpoint.
   *
   * @throws Exception if the test fails
   */
  @Test
  void dropStudentTest() throws Exception {
    mockMvc.perform(patch("/dropStudentFromCourse")
            .param("deptCode", "COMS")
            .param("courseCode", "3"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));

    int currentEnrolledStudents = 9;
    for (int i = 0; i < currentEnrolledStudents; i++) {
      mockMvc.perform(patch("/dropStudentFromCourse")
              .param("deptCode", "PSYC")
              .param("courseCode", "4493"))
          .andExpect(status().isOk())
          .andExpect(content().string("Student has been dropped."));
    }

    mockMvc.perform(patch("/dropStudentFromCourse")
            .param("deptCode", "PSYC")
            .param("courseCode", "4493"))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("Student has not been dropped."));

  }

  /**
   * Tests the setEnrollmentCount endpoint.
   *
   * @throws Exception if the test fails
   */
  @Test
  void setEnrollmentCountTest() throws Exception {
    // invalid count input
    mockMvc.perform(patch("/setEnrollmentCount")
            .param("deptCode", "COMS")
            .param("courseCode", "1004")
            .param("count", "20a"))
        .andExpect(status().isBadRequest());

    mockMvc.perform(patch("/setEnrollmentCount")
            .param("deptCode", "COMS")
            .param("courseCode", "1004")
            .param("count", "-1"))
        .andExpect(status().isBadRequest());

    mockMvc.perform(patch("/setEnrollmentCount")
            .param("deptCode", "COMS")
            .param("courseCode", "3")
            .param("count", "200"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));

    mockMvc.perform(patch("/setEnrollmentCount")
            .param("deptCode", "COMS")
            .param("courseCode", "1004")
            .param("count", "200"))
        .andExpect(status().isOk())
        .andExpect(content().string("Attributed was updated successfully."));
  }

  /**
   * Tests the changeCourseTime endpoint.
   *
   * @throws Exception if the test fails
   */
  @Test
  void changeCourseTimeTest() throws Exception {
    mockMvc.perform(patch("/changeCourseTime")
            .param("deptCode", "COMS")
            .param("courseCode", "3")
            .param("time", "14:00-15:15"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));

    mockMvc.perform(patch("/changeCourseTime")
            .param("deptCode", "COMS")
            .param("courseCode", "1004")
            .param("time", "14:00-15:15"))
        .andExpect(status().isOk())
        .andExpect(content().string("Attributed was updated successfully."));

    mockMvc.perform(get("/findCourseTime")
            .param("deptCode", "COMS")
            .param("courseCode", "1004"))
        .andExpect(status().isOk())
        .andExpect(content().string("The course meets at: 14:00-15:15."));
  }

  /**
   * Tests the changeCourseTeacher endpoint.
   *
   * @throws Exception if the test fails
   */
  @Test
  void changeCourseTeacherTest() throws Exception {
    mockMvc.perform(patch("/changeCourseTeacher")
            .param("deptCode", "COMS")
            .param("courseCode", "3")
            .param("teacher", "Mike"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));

    mockMvc.perform(patch("/changeCourseTeacher")
            .param("deptCode", "COMS")
            .param("courseCode", "1004")
            .param("teacher", "Mike"))
        .andExpect(status().isOk())
        .andExpect(content().string("Attributed was updated successfully."));
    mockMvc.perform(get("/findCourseInstructor")
            .param("deptCode", "COMS")
            .param("courseCode", "1004"))
        .andExpect(status().isOk())
        .andExpect(content().string("Mike is the instructor for the course."));
  }

  /**
   * Tests the changeCourseLocation endpoint.
   *
   * @throws Exception if the test fails
   */
  @Test
  void changeCourseLocationTest() throws Exception {
    mockMvc.perform(patch("/changeCourseLocation")
            .param("deptCode", "COMS")
            .param("courseCode", "3")
            .param("location", "833 MUDD"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));

    mockMvc.perform(patch("/changeCourseLocation")
            .param("deptCode", "COMS")
            .param("courseCode", "1004")
            .param("location", "833 MUDD"))
        .andExpect(status().isOk())
        .andExpect(content().string("Attributed was updated successfully."));
    mockMvc.perform(get("/findCourseLocation")
            .param("deptCode", "COMS")
            .param("courseCode", "1004"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("833 MUDD")));
  }
}