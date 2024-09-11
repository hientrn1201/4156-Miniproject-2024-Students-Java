package dev.coms4156.project.individualproject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * This class represents a file-based database containing department mappings.
 */
public class MyFileDatabase {

  /**
   * Constructs a MyFileDatabase object and loads up the data structure with
   * the contents of the file.
   *
   * @param flag     used to distinguish mode of database
   * @param filePath the path to the file containing the entries of the database
   */
  public MyFileDatabase(int flag, String filePath) {
    this.filePath = filePath;
    if (flag == 0) {
      this.departmentMapping = deSerializeObjectFromFile();
    }
  }

  /**
   * Sets the department mapping of the database.
   *
   * @param mapping the mapping of department names to Department objects
   */
  public void setMapping(HashMap<String, Department> mapping) {
    this.departmentMapping = mapping;
  }

  /**
   * Deserializes the object from the file and returns the department mapping.
   *
   * @return the deserialized department mapping
   */
  public HashMap<String, Department> deSerializeObjectFromFile() {
    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
      Object obj = in.readObject();
      if (obj instanceof HashMap<?, ?> tempMap) {
        HashMap<String, Department> checkedMap = new HashMap<>();

        for (Map.Entry<?, ?> entry : tempMap.entrySet()) {
          // Check if the key is a String and the value is a Department
          if (entry.getKey() instanceof String && entry.getValue() instanceof Department) {
            checkedMap.put((String) entry.getKey(), (Department) entry.getValue());
          } else {
            throw new IllegalArgumentException("Invalid map key or value type.");
          }
        }
        return checkedMap;
      } else {
        throw new IllegalArgumentException("Invalid object type in file.");
      }
    } catch (IOException | ClassNotFoundException e) {
      logger.severe("Error deserialize object from file.");
      logger.severe(e.toString());
      return null;
    }
  }

  /**
   * Saves the contents of the internal data structure to the file. Contents of the file are
   * overwritten with this operation.
   */
  public void saveContentsToFile() {
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
      out.writeObject(departmentMapping);
      System.out.println("Object serialized successfully.");
    } catch (IOException e) {
      logger.severe("Error saving object to file.");
      logger.severe(e.toString());
    }
  }

  /**
   * Gets the department mapping of the database.
   *
   * @return the department mapping
   */
  public HashMap<String, Department> getDepartmentMapping() {
    return this.departmentMapping;
  }

  /**
   * Returns a string representation of the database.
   *
   * @return a string representation of the database
   */
  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    for (Map.Entry<String, Department> entry : departmentMapping.entrySet()) {
      String key = entry.getKey();
      Department value = entry.getValue();
      result.append("For the ").append(key).append(" department: \n").append(value.toString());
    }
    return result.toString();
  }

  /** The path to the file containing the database entries. */
  private final String filePath;

  /** The mapping of department names to Department objects. */
  private HashMap<String, Department> departmentMapping;

  /** Logger instance for MyFileDatabase instance. */
  private static final Logger logger = Logger.getLogger(MyFileDatabase.class.getName());
}
