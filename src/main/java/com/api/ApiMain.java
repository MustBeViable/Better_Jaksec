package com.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//exclude pitää poistaa kun lisätään DB mukaa toimintaan
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class ApiMain {
    public static void main(String[] args) {
        SpringApplication.run(ApiMain.class, args);
    }
}
