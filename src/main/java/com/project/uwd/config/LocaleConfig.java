package com.project.uwd.config;

import java.util.HashMap;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;


@Configuration
public class LocaleConfig {
	
	@Bean
    LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(new Locale("sr", "RS"));
        return localeResolver;
    }
	
	@Bean
	LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		
		return localeChangeInterceptor;
	}
	
	@Bean
    MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("lang/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
	
	@Bean(name = {"memorijaAplikacije"})
	ApplicationMemory getApplicationMemory() {
		return new ApplicationMemory();
	}
	
	public class ApplicationMemory extends HashMap {
		
	}
}
