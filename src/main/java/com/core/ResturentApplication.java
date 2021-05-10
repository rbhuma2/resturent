package com.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.core.exception.ExceptionControllerHandler;

@SpringBootApplication
@EnableTransactionManagement
@EnableMongoRepositories(basePackages = { "com.core.mongo.data" })
@Import(ExceptionControllerHandler.class)
public class ResturentApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResturentApplication.class, args);
	}
	
	@Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:i18n/message");
        return messageSource;
    }

}
