package com.taskmanager.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Task Management System API")
                        .description("A comprehensive Task Management System built with Spring Boot and JPA. " +
                                   "This API allows you to manage users and tasks with role-based access control.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Task Management Team")
                                .email("support@taskmanager.com")
                                .url("https://taskmanager.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("https://springboot-task-manager-production.up.railway.app/api")
                                .description("Railway Production Server"),
                        new Server()
                                .url("http://localhost:8080/api")
                                .description("Local Development Server")
                ));
    }
}
