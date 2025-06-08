package com.example.springpractice;

import com.example.springpractice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

@SpringBootApplication
public class SpringPracticeApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringPracticeApplication.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(SpringPracticeApplication.class, args);
	}

	@Bean
	public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer() {
		return factory -> factory.addErrorPages(
				new ErrorPage(HttpStatus.NOT_FOUND, "/404")
		);
	}

	@Bean
	public CommandLineRunner testConnection(UserRepository repo) {
		return args -> {
			long count = repo.count();
			System.out.println("Users count in DB: " + count);
		};
	}
}