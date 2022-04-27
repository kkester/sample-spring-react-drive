package io.pivotal.places;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {"io.pivotal.drive", "io.pivotal.places"})
public class PlacesApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlacesApplication.class, args);
	}

}
