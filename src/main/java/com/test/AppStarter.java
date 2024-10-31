package com.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@SpringBootApplication
public class AppStarter extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(AppStarter.class, args);
		System.out.println("----------------- App-Started -----------------");
	}

//	Used to run the Application from WAR file deployed on external tomcat
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(new Class[] { AppStarter.class });
	}

	@Bean
	public RestClient restClient() {
		return RestClient.builder()
				.baseUrl("https://jsonplaceholder.typicode.com")
				.build();
	}

}
