//package com.example.core.converter;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.format.FormatterRegistry;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//@Configuration
//@EnableWebMvc
//public class WebMvcContext extends WebMvcConfigurerAdapter {
//
//	@Override
//	public void addFormatters(FormatterRegistry registry) {
//		registry.addConverter(new LocalDateConverter("MM/dd/yyyy"));
//		registry.addConverter(new LocalDateTimeConverter("MM/dd/yyyy'T'HH:mm:ss.SSS"));
//	}
//}