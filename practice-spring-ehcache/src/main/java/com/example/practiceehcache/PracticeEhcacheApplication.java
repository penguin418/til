package com.example.practiceehcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class PracticeEhcacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(PracticeEhcacheApplication.class, args);
    }

}
