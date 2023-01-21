package com.github.penguin418.mailservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@SpringBootApplication
public class MessageApiApp {

    public static void main(String[] args) {
        SpringApplication.run(MessageApiApp.class, args);
    }
}
