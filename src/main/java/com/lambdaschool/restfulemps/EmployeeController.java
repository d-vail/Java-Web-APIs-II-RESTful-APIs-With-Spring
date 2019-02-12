package com.lambdaschool.restfulemps;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

// Post -> Create
// Get -> Read
// Put -> Update/Replace
// Patch -> Update/Modify
// Delete -> Delete

@RestController
public class EmployeeController {
  private final EmployeeRepository empRepos;
  private final EmployeeResourceAssembler assembler;

  public EmployeeController(EmployeeRepository empRepos, EmployeeResourceAssembler assembler) {
    this.empRepos = empRepos;
    this.assembler = assembler;
  }

  // Shortcut for RequestMapping(method=RequestMethod.GET)
  @GetMapping("/employees")
  public Resources<Resource<Employee>> all() {
    // Create a list of the Employee Resource
    // Find all from the employee repository
    List<Resource<Employee>> employees = empRepos.findAll().stream()
            .map(assembler::toResource)
            .collect(Collectors.toList());
    return new Resources<>(employees,
            linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
  }

  @GetMapping("/employees/{id}")
  public Resource<Employee> findOne(@PathVariable Long id) {
    Employee employee = empRepos.findById(id)
            .orElseThrow(() -> new EmployeeNotFoundException(id));

    return assembler.toResource(employee);
  }

  // If employee is found update it, if not found add new record
  // Throw exception if incoming data format is incorrect
  @PutMapping("/employees/{id}")
  public ResponseEntity<?> putEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) throws URISyntaxException {
    Employee employeeToUpdate = empRepos.findById(id)
            .map(employee -> {
              employee.setFname(newEmployee.getFname());
              employee.setLname(newEmployee.getLname());
              employee.setSalary(newEmployee.getSalary());
              employee.setHas401k(newEmployee.isHas401k());
              return empRepos.save(employee);
            }).orElseGet(() -> {
              newEmployee.setId(id);
              return empRepos.save(newEmployee);
            });

    Resource<Employee> resource = assembler.toResource(employeeToUpdate);

    return ResponseEntity
            .created(new URI(resource.getId().expand().getHref()))
            .body(resource);
  }
}
