package com.lambdaschool.restfulemps;

public class EmployeeNotFoundException extends RuntimeException {
  public EmployeeNotFoundException(long id) {
    super("Could not find employee " + id);
  }
}
