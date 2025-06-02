package com.tss.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class ApplicationConfig {

    @Autowired
    private Environment env;

    @Qualifier("dataSourceAuth")
    @Bean
    @ConfigurationProperties("spring.authdatasource")
    public DataSource dataSourceAuth() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.authdatasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.authdatasource.url"));
        dataSource.setUsername(env.getProperty("spring.authdatasource.username"));
        dataSource.setPassword(env.getProperty("spring.authdatasource.password"));
        return dataSource;
    }
}