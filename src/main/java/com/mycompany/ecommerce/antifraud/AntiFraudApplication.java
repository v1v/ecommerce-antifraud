package com.mycompany.ecommerce.antifraud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@SpringBootApplication
@RestController
public class AntiFraudApplication {

	@Value("${pom.version}")
	private String pomVersion;

	public static void main(String[] args) {
		SpringApplication.run(AntiFraudApplication.class, args);
	}

	@GetMapping("/ecommerce")
	public String ecommerce(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

	@RequestMapping(value = "/healthcheck", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map healthcheck() {
		return Collections.singletonMap("status", "Up");
	}

	@RequestMapping(value = "/version", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map version() {
		return Collections.singletonMap("version", pomVersion);
	}
}
