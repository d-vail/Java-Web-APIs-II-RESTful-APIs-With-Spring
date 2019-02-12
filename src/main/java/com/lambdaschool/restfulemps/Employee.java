package com.lambdaschool.restfulemps;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data   // creates getters, setters, toString -> takes care of boilerplate
@Entity // object ready for JPA storage
public class Employee {
  private @Id @GeneratedValue Long id;  // primary key automatically populated
  private String fname;
  private String lname;
  private boolean has401k;
  private double salary;

  // Default Constructor -- Necessary for JPA
  public Employee () {}

  public Employee(String fname, String lname, boolean has401k, double salary) {
    this.fname = fname;
    this.lname = lname;
    this.has401k = has401k;
    this.salary = salary;
  }
}
