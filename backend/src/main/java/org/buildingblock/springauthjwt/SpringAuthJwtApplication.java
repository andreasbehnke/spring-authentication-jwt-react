package org.buildingblock.springauthjwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {
		"org.buildingblock.springauthjwt",
		"org.springext.security.jwt"
})
public class SpringAuthJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAuthJwtApplication.class, args);
	}

}
