package com.github.penguin418.mailservice.controller;

import com.github.penguin418.mailservice.model.MailDto;
import com.github.penguin418.mailservice.service.MailService;
import com.github.penguin418.mailservice.service.MailServiceQueue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MailController {
    private final MailServiceQueue mailServiceQueue;

    @PostMapping("/api/mail/submit")
    public void sendMail(@RequestBody MailDto mailDto){
        log.info("request sendmail");
        mailServiceQueue.sendMail(mailDto);
    }

    @PostMapping("/api/mail/sync")
    public void syncMail(){
        log.info("request sendmail");
        mailServiceQueue.syncMail();
    }
}
