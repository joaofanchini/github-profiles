package com.projeto.teste.githubprofilesback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.server.Encoding;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class GithubProfilesBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(GithubProfilesBackApplication.class, args);
	}

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("messages");
		messageSource.setDefaultEncoding(StandardCharsets.ISO_8859_1.name());
		return messageSource;
	}

}
