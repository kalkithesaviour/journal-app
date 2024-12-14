package com.vishal.journalbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.vishal.journalbackend.model.SentimentData;

@Service
public class SentimentConsumerService {

    private final EmailService emailService;

    @Autowired
    public SentimentConsumerService(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "weekly-sentiments", groupId = "weekly-sentiment-group")
    public void consume(SentimentData sentimentData) {
        emailService.sendEmail(sentimentData.getEmail(), "Sentiment for previous week", sentimentData.getSentiment());
    }

}
