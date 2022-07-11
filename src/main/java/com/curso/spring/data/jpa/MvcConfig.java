package com.curso.spring.data.jpa;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		
		registry.addResourceHandler("/uploads/**")
		// Como es un directorio fisico ubicamos File
		.addResourceLocations("file:/D:/imgSpring/uploads/");
		
	}
	
	

}
