package com.github.penguin418.mailservice.service;

import com.github.penguin418.mailservice.model.MailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.github.penguin418.mailservice.config.MailClientQueueConfig.*;

@Slf4j
@Service
public class MailServiceQueue implements MailService{
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendMail(MailDto mailDto){
        log.info("enqueue sendmail");
        rabbitTemplate.convertAndSend(EXCHANGE, SENDMAIL_ROUTING_KEY, mailDto);
    }

    @Override
    public void syncMail(){
        log.info("enqueue syncmail");
        rabbitTemplate.convertAndSend(EXCHANGE, SYNCMAIL_ROUTING_KEY, "username");
    }
}
