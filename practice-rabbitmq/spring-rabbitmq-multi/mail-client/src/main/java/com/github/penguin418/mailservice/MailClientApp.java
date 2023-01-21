package com.github.penguin418.mailservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class MailClientApp {

    public static void main(String[] args) {
        SpringApplication.run(MailClientApp.class, args);
    }
}