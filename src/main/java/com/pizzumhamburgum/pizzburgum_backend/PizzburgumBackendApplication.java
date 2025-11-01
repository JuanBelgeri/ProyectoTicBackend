package com.pizzumhamburgum.pizzburgum_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class})
@EnableJpaRepositories(basePackages = "repository")
@ComponentScan(basePackages = {"controller", "service", "config", "security", "com.pizzumhamburgum.pizzburgum_backend"})
@EntityScan(basePackages = "model")
public class PizzburgumBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(PizzburgumBackendApplication.class, args);
    }

}
