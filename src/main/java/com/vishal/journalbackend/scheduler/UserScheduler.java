package com.vishal.journalbackend.scheduler;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.vishal.journalbackend.cache.AppCache;
import com.vishal.journalbackend.entity.JournalEntry;
import com.vishal.journalbackend.entity.User;
import com.vishal.journalbackend.enums.Sentiment;
import com.vishal.journalbackend.repository.UserRepositoryImpl;
import com.vishal.journalbackend.service.EmailService;

@Component
public class UserScheduler {

    private final EmailService emailService;
    private final UserRepositoryImpl userRepositoryImpl;
    private final AppCache appCache;

    public UserScheduler(EmailService emailService, UserRepositoryImpl userRepositoryImpl, AppCache appCache) {
        this.emailService = emailService;
        this.userRepositoryImpl = userRepositoryImpl;
        this.appCache = appCache;
    }

    @Scheduled(cron = "0 0 9 ? * SUN *")
    public void fetchUsersAndSendSaMail() {
        List<User> users = userRepositoryImpl.getUsersForSentimentAnalysis();
        for (User user : users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream()
                    .filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                    .map(x -> x.getSentiment()).toList();

            Map<Sentiment, Integer> sentimentCounts = new EnumMap<>(Sentiment.class);
            for (Sentiment sentiment : sentiments) {
                if (sentiment != null) {
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
                }
            }

            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }

            if (mostFrequentSentiment != null) {
                emailService.sendEmail(user.getEmail(), "Sentiment for the last 7 days",
                        mostFrequentSentiment.toString());
            }
        }
    }

    @Scheduled(cron = "0 0/10 * 1/1 * ? *")
    public void clearAppCache() {
        appCache.init();
    }

}
