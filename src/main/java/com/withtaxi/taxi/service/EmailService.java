package com.withtaxi.taxi.service;

public interface EmailService {
    String sendSimpleMessage(String to) throws Exception;

    int issuedTemporaryPassword(String userId);
}
