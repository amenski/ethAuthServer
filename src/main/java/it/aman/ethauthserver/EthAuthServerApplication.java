package it.aman.ethauthserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class EthAuthServerApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(EthAuthServerApplication.class, args);
	}

}
