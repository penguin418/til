package com.github.penguin418.mailservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailClientQueueConfig {
    public final static String EXCHANGE = "direct.exchange";
    public final static String SENDMAIL_QUEUE_NAME = "mailclient.sendmailqueue";
    public final static String SENDMAIL_ROUTING_KEY = "mailclient.routekey.sendmail";
    public final static String SYNCMAIL_QUEUE_NAME = "mailclient.syncmailqueue";
    public final static String SYNCMAIL_ROUTING_KEY = "mailclient.routekey.syncmail";

    @Bean
    public DirectExchange mailClientExchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Queue sendMailQueue() {
        return new Queue(SENDMAIL_QUEUE_NAME, false);
    }

    @Bean
    public Queue syncMailQueue() {
        return new Queue(SYNCMAIL_QUEUE_NAME, false);
    }

    @Bean
    public Binding  sendMailBinding(){
        return BindingBuilder.bind(sendMailQueue()).to(mailClientExchange()).with(SENDMAIL_ROUTING_KEY);
    }
    @Bean
    public Binding  syncMailBinding(){
        return BindingBuilder.bind(syncMailQueue()).to(mailClientExchange()).with(SYNCMAIL_ROUTING_KEY);
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
