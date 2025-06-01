package com.tss.SpringMvcShowcase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages={"com.tss"})
public class SpringMvcShowcaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMvcShowcaseApplication.class, args);
	}

}
