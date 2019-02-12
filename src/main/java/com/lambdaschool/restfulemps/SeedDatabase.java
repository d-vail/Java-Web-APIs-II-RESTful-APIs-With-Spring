package com.lambdaschool.restfulemps;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j          // Lombok annotation -> auto creates Slf4j-based logs
@Configuration  // Indicates that a class declares one or more @Beans -> part of Spring
                // Bean -> object or method controlled by Spring. Contains Java + metadata
public class SeedDatabase {
  @Bean
  // CommandLineRunner -> Spring Boot runs all Beans at startupl perfect for seeding/init app data
  public CommandLineRunner initDB(EmployeeRepository empRepos) {
    return args -> {
      // Save employee record and log action
      log.info("Seeding " + empRepos.save(new Employee("Steve", "Green", true, 45000)));
      log.info("Seeding " + empRepos.save(new Employee("May", "Ford", true, 80000)));
      log.info("Seeding " + empRepos.save(new Employee("John", "Jones", true, 75000)));
    };
  }
}
