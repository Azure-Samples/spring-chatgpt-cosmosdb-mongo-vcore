package com.microsoft.azure.spring.chatgpt.sample.webapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication //(exclude = {MongoAutoConfiguration.class})
@EnableMongoRepositories(basePackages = "com.microsoft.azure.spring.chatgpt.sample.common.vectorstore")

public class SpringChatgptSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringChatgptSampleApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**");
			}
		};
	}
}
