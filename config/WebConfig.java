package com.kingsmen.kingsreachdev.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	 @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/api/**")  // Allow CORS for all API endpoints
	                .allowedOrigins("http://localhost:5173/")  // React app is usually hosted on this port during development
	                .allowedMethods("GET", "POST", "PUT", "DELETE")
	                .allowedHeaders("*")
	                .allowCredentials(true);
	    }
}




