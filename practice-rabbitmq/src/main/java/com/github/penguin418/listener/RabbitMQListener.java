package com.github.penguin418.listener;

import com.github.penguin418.constant.RabbitMQConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMQListener {

    @RabbitListener(queues = RabbitMQConstant.QUEUE_NAME)
    public void receiveMessage(Message message) {
        log.info("receive message :{}", message);
    }
}
