package com.vishal.journalbackend.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmailServiceTests {

    private final EmailService emailService;

    @Autowired
    public EmailServiceTests(EmailService emailService) {
        this.emailService = emailService;
    }

    @Disabled
    @Test
    void testSendMail() {
        assertDoesNotThrow(() -> emailService.sendEmail(
                "frodobaggins08062018@gmail.com",
                "Testing Java mail sender",
                "Hi, sup dude?"));
    }

}
