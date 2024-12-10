package org.unibl.etf.springlearning.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.unibl.etf.springlearning.services.EmailService;

@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String emailAddress;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void send(String address,String subject, String content) {
        SimpleMailMessage mail=new SimpleMailMessage();
        mail.setFrom(emailAddress);
        mail.setSubject(subject);
        mail.setTo(address);
        mail.setText(content);
        javaMailSender.send(mail);
    }
}
