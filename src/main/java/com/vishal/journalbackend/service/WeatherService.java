package com.vishal.journalbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.vishal.journalbackend.api.response.WeatherResponse;
import com.vishal.journalbackend.cache.AppCache;
import com.vishal.journalbackend.constant.Placeholders;
import com.vishal.journalbackend.enums.Key;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final AppCache appCache;
    private final RedisService redisService;

    @Autowired
    public WeatherService(RestTemplate restTemplate, AppCache appCache, RedisService redisService) {
        this.restTemplate = restTemplate;
        this.appCache = appCache;
        this.redisService = redisService;
    }

    public WeatherResponse getWeather(String city) {
        WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);
        if (weatherResponse != null) {
            return weatherResponse;
        }
        String finalAPI = appCache.getAppCacheMap().get(Key.WEATHER_API_KEY.toString())
                .replace(Placeholders.CITY, city)
                .replace(Placeholders.API_KEY, apiKey);
        weatherResponse = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class).getBody();
        if (weatherResponse != null) {
            redisService.set("weather_of_" + city, weatherResponse, 300L);
        }
        return weatherResponse;
    }

}
