package com.fc.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.fc.api", "com.fc.core", "com.fc.domain"})
public class ApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(ApiApplication.class, args);
  }

}
