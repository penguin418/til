package com.github.penguin418.mailservice.listener;

import com.github.penguin418.mailservice.model.MailDto;
import com.github.penguin418.mailservice.service.MailServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static com.github.penguin418.mailservice.config.MailClientQueueConfig.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMQListener {

    private final MailServiceImpl mailServiceImpl;

    @RabbitListener(queues = SENDMAIL_QUEUE_NAME)
    public void receiveSendMailRequest(MailDto mailDto) {
        log.info("deque sendmail");
        mailServiceImpl.sendMail(mailDto);
    }

    @RabbitListener(queues = SYNCMAIL_QUEUE_NAME)
    public void receiveSyncMailRequest(String user) {
        log.info("deque sycnmail");
        mailServiceImpl.syncMail();
    }
}
