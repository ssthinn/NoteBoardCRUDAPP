package com.app.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;  
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource; 

@Configuration
public class DatabaseConfig {
	
	@Value("${spring.datasource.password}")
    private String dbPassword;
    @Value("${spring.datasource.username}")
    private String dbUsername;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.url}")
    private String url;
	
	@Bean  
    DataSource dataSource() {  
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName(driverClassName);
	    dataSource.setUrl(url);
	    dataSource.setUsername(dbUsername);
	    dataSource.setPassword(dbPassword);
	    return dataSource;
    }
}
