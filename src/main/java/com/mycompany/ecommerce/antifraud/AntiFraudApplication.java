package com.mycompany.ecommerce.antifraud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
		String prefix = "Bye";
		return String.format("%s %s!", prefix, name);
	}

	@RequestMapping(value = "/healthcheck", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map healthcheck() {
		// Comment out to generate a synthetic error
		//generateError();
		return Collections.singletonMap("status", "Up");
	}

	@RequestMapping(value = "/version", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map version() {
		return Collections.singletonMap("version", pomVersion);
	}

	private void generateError() {
		// Comment out to generate a synthetic error
		/*if (pomVersion.equals("0.0.3")) {
			// Generate a synthetic error if version is not right
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
		}*/
	}
}
