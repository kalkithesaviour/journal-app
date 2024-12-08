package com.vishal.journalbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.vishal.journalbackend.api.response.WeatherResponse;
import com.vishal.journalbackend.cache.AppCache;
import com.vishal.journalbackend.constant.Placeholders;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final AppCache appCache;

    @Autowired
    public WeatherService(RestTemplate restTemplate, AppCache appCache) {
        this.restTemplate = restTemplate;
        this.appCache = appCache;
    }

    public WeatherResponse getWeather(String city) {
        String finalAPI = appCache.getAppCacheMap().get(AppCache.keys.WEATHER_API_KEY.toString())
                .replace(Placeholders.CITY, city)
                .replace(Placeholders.API_KEY, apiKey);
        return restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class).getBody();
    }

}
