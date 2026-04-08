package com.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//exclude pitää poistaa kun lisätään DB mukaa toimintaan
/**
 * This is the main method for API for now it doesn't need anything else to run
 * see @Link AdminController.java for example for routing.
 * After starting the method api works in http://localhost:8081/api/admin for example
 */
@SpringBootApplication
public class ApiMain {
    public static void main(String[] args) {
        SpringApplication.run(ApiMain.class, args);
    }
}
