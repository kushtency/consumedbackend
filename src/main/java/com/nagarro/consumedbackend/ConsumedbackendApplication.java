package com.nagarro.consumedbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class ConsumedbackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumedbackendApplication.class, args);
	}

}
