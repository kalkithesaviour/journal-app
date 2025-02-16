package com.vishal.journalbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.vishal.journalbackend")
@EnableScheduling
@EnableKafka
public class JournalBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(JournalBackendApplication.class, args);
	}

}
