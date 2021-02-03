package com.bolsadeideas.springboot.app;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
	
	
	@Autowired 
	private DataSource dataSource;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		WebMvcConfigurer.super.addResourceHandlers(registry);
		
		registry.addResourceHandler("/archivos/**")
		.addResourceLocations("file:C:/erwincosas/cursoSpring5/archivos/");
		
		registry.addResourceHandler("/reportes/**")
		.addResourceLocations("file:C:/erwincosas/cursoSpring5/reportes/");
	}
	
	
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/error_403").setViewName("error_403");
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public Jaxb2Marshaller jaxb2Marshaller() {
		     
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(new Class[]{com.bolsadeideas.springboot.app.view.xml.ClienteListXml.class});
		
		return marshaller;
		
	}
	
	@Bean
    public JdbcTemplate jdbcTemplate() {

		JdbcTemplate template = new JdbcTemplate();
        template.setDataSource(dataSource);

        return template;
    }
	

}
