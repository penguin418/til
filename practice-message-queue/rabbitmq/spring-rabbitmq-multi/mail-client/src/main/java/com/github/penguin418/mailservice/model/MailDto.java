package com.github.penguin418.mailservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MailDto {
    private String fromAddress;
    private List<String> toAddresses = new ArrayList<String>();
    private List<String> ccAddresses = new ArrayList<String>();
    private List<String> bccAddresses = new ArrayList<String>();
    private String subject;
    private String text;
}