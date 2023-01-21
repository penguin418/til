package com.github.penguin418.mailservice.service;

import com.github.penguin418.mailservice.model.MailDto;

public interface MailService {
    void sendMail(MailDto mailDto);

    void syncMail();
}
