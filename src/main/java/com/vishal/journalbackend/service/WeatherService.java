package com.vishal.journalbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.vishal.journalbackend.api.response.WeatherResponse;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String API_KEY;

    private static final String API = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    private final RestTemplate restTemplate;

    @Autowired
    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherResponse getWeather(String city) {
        String finalAPI = API.replace("CITY", city).replace("API_KEY", API_KEY);
        return restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class).getBody();
    }

}
