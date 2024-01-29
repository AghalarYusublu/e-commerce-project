package com.example.notificationservice.presentation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class CommonConfig {
    @Bean
    JavaMailSender mailSender() {
        return new JavaMailSenderImpl();
    }
}
