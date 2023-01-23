package com.github.penguin418.mailservice.service;

import com.github.penguin418.mailservice.model.MailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    public void sendMail(MailDto mailDto) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            InternetAddress[] toAddress = toInternetAddresses(mailDto.getToAddresses());
            InternetAddress[] ccAddress = toInternetAddresses(mailDto.getCcAddresses());
            InternetAddress[] bccAddress = toInternetAddresses(mailDto.getBccAddresses());

            mimeMessageHelper.setSubject(MimeUtility.encodeText(mailDto.getSubject(), "UTF-8", "B"));
            mimeMessageHelper.setText(mailDto.getText());
            mimeMessageHelper.setFrom(new InternetAddress(mailDto.getFromAddress(), mailDto.getFromAddress(), "UTF-8"));
            mimeMessageHelper.setTo(toAddress);
            mimeMessageHelper.setCc(ccAddress);
            mimeMessageHelper.setBcc(bccAddress);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void syncMail() {
    }

    private InternetAddress[] toInternetAddresses(List<String> addresses) {
        List<InternetAddress> internetAddresses = new ArrayList<>();
        for(String address : addresses){
            try {
                internetAddresses.add(new InternetAddress(address, address, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return internetAddresses.toArray(InternetAddress[]::new);
    }


}