package com.opsly.app.integration.config;


import org.springframework.context.annotation.Profile;

@Profile("${spring.profiles.active}")
public class IntegrationTestConfig {

}
