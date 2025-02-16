package com.vishal.journalbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.vishal.journalbackend.model.SentimentData;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SentimentConsumerService {

    private final EmailService emailService;

    @Autowired
    public SentimentConsumerService(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostConstruct
    public void init() {
        log.info("SentimentConsumerService initialized");
    }

    @KafkaListener(topics = "weekly-sentiments", groupId = "weekly-sentiment-group")
    public void consume(SentimentData sentimentData) {
        log.info("Kafka Listener triggered");
        log.info("Sending mail to: " + sentimentData.getEmail());
        log.info("Email content: " + sentimentData.getSentiment());
        emailService.sendEmail(sentimentData.getEmail(), "Sentiment for previous week", sentimentData.getSentiment());
    }

}
