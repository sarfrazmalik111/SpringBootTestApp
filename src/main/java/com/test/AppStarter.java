package com.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import java.time.Duration;

@SpringBootApplication
@EnableScheduling
public class AppStarter extends SpringBootServletInitializer {
	
	public static void main(String[] args) {
		SpringApplication.run(AppStarter.class, args);
		System.out.println("--------AppMainRunner-Started--------");
	}

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(new Class[] { AppStarter.class });
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder
//				.basicAuthentication("admin", "password")
//				.errorHandler(new RestTemplateResponseErrorHandler())
				.setConnectTimeout(Duration.ofMillis(45000))
				.setReadTimeout(Duration.ofMillis(45000))
				.build();
	}

}
