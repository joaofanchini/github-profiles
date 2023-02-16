package com.projeto.teste.githubprofilesback;

import com.projeto.teste.githubprofilesback.converters.ConverterFacade;
import com.projeto.teste.githubprofilesback.converters.impl.Converter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.server.Encoding;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ResourceBundleMessageSource;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.List;

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

	@Bean
	@Scope("singleton")
	public ConverterFacade converterFacade(List<Converter> converters){
		return new ConverterFacade(converters);
	}

}
