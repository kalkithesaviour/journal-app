package com.vishal.journalbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JournalBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(JournalBackendApplication.class, args);
	}

}
