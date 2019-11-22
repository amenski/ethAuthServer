package it.aman.ethauthserver;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class EthAuthServerApplication extends SpringBootServletInitializer {

	static final String API_VERSION = "ETH_AUTH_SERVER_VERSION";
	
	public static void main(String[] args) {
		SpringApplication.run(EthAuthServerApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(EthAuthServerApplication.class);
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		String implementationVersion="";
		try {
			InputStream manifestInput = servletContext.getResourceAsStream("META-INF/MANIFEST.MF");
			if(manifestInput != null){
				Manifest manifest = new Manifest(manifestInput);
				implementationVersion = (String) manifest.getMainAttributes().get(Attributes.Name.IMPLEMENTATION_VERSION);
				System.setProperty(EthAuthServerApplication.API_VERSION, implementationVersion);
			}
		}catch(IOException e) {
			logger.error("Unable to read manifest file.");
		}
		super.onStartup(servletContext);
	}

}
