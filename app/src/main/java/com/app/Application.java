package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.app", "com.vehicle", "com.station"})
@EnableJpaRepositories(basePackages = {"com.vehicle.repository", "com.station.repository"})
@EntityScan(basePackages = "com.vehicle.model, com.shared.model, com.station.model")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
