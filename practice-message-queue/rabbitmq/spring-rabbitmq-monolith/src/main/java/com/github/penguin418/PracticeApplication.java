package com.github.penguin418;

import com.github.penguin418.model.RequestA;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.IntStream;

import static com.github.penguin418.constant.RabbitMQConstant.TASK1_ROUTING_KEY;
import static com.github.penguin418.constant.RabbitMQConstant.TOPIC_NAME;

@Slf4j
@SpringBootApplication
public class PracticeApplication {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public static void main(String[] args) {
        SpringApplication.run(PracticeApplication.class, args);

    }

    @Bean
    public CommandLineRunner run() throws Exception {
        return (String[] args) -> {
            IntStream.range(0,10)
                    .forEach(i-> {
                        RequestA message = new RequestA();
                        message.setText("do job[" + i + "]");
                        log.info("send message :{}", message);
                        new MessageProperties().setExpiration();
                        rabbitTemplate.convertAndSend(TOPIC_NAME, TASK1_ROUTING_KEY, message);
                    });
        };
    }
}