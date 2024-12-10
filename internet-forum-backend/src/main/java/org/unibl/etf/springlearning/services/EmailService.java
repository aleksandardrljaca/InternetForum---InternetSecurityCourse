package org.unibl.etf.springlearning.services;

public interface EmailService {
    void send(String address,String subject,String content);
}
