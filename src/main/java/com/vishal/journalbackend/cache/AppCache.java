package com.vishal.journalbackend.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vishal.journalbackend.entity.ConfigJournalApp;
import com.vishal.journalbackend.repository.ConfigJournalAppRepository;

import jakarta.annotation.PostConstruct;
import lombok.Getter;

@Component
@Getter
public class AppCache {

    public enum keys {
        WEATHER_API_KEY
    }

    private final ConfigJournalAppRepository configJournalAppRepository;

    @Autowired
    public AppCache(ConfigJournalAppRepository configJournalAppRepository) {
        this.configJournalAppRepository = configJournalAppRepository;
    }

    private Map<String, String> appCacheMap;

    @PostConstruct
    public void init() {
        appCacheMap = new HashMap<>();
        List<ConfigJournalApp> all = configJournalAppRepository.findAll();
        all.forEach(configJournalApp -> appCacheMap.put(configJournalApp.getKey(), configJournalApp.getValue()));
    }

}
