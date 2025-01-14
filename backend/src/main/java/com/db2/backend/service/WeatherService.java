package com.db2.backend.service;

import com.db2.backend.model.WeatherData;
import com.db2.backend.model.OpenWeatherResponse;
import com.db2.backend.exception.WeatherException;
import com.db2.backend.repository.WeatherRepository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    private final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.base-url}")
    private String baseUrl;

    private void updateWeatherDataFromResponse(WeatherData weatherData, OpenWeatherResponse response) {
        weatherData.setName(response.getName());
        weatherData.setTemp(response.getMain().getTemp());
        weatherData.setFeelsLike(response.getMain().getFeels_like());
        weatherData.setTempMin(response.getMain().getTemp_min());
        weatherData.setTempMax(response.getMain().getTemp_max());
        weatherData.setDt(response.getDt());
    }

    public WeatherData getWeatherDataByCity(String cityName) {
        logger.debug("Fetching weather data for city: {}", cityName);

        if (cityName == null || cityName.trim().isEmpty()) {
            logger.error("Attempt to fetch weather with empty city name");
            throw new WeatherException("City name cannot be empty");
        }

        String url = String.format("%s/weather?q=%s&appid=%s&units=metric", baseUrl, cityName, apiKey);
        logger.debug("Calling OpenWeather API with URL: {}", url.replace(apiKey, "***"));

        try {
            ResponseEntity<OpenWeatherResponse> response = restTemplate.getForEntity(url, OpenWeatherResponse.class);
            OpenWeatherResponse weatherResponse = response.getBody();

            if (weatherResponse == null) {
                logger.error("Received null response for city: {}", cityName);
                throw new WeatherException("No weather data received for city: " + cityName);
            }

            logger.debug("Successfully received weather data for city: {}", cityName);

            WeatherData weatherData = new WeatherData();
            updateWeatherDataFromResponse(weatherData, weatherResponse);
            
            logger.info("Saving weather data for city: {}", cityName);
            weatherRepository.save(weatherData);
            
            logger.info("Fetching saved weather data for city: {}", cityName);
            return weatherRepository.findByName(cityName);

        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                logger.error("City not found: {}", cityName);
                throw new WeatherException("City not found: " + cityName);
            }
            logger.error("Error fetching weather data for city: {}", cityName, e);
            throw new WeatherException("Error fetching weather data: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Unexpected error processing weather data for city: {}", cityName, e);
            throw new WeatherException("Failed to process weather data for " + cityName, e);
        }
    }

    public List<String> getAllCityNames() {
        logger.debug("Fetching all city names from database");
        try {
            List<String> cities = weatherRepository.findAllNames();
            logger.debug("Successfully retrieved {} cities", cities.size());
            return cities;
        } catch (Exception e) {
            logger.error("Error fetching city names from database", e);
            throw new WeatherException("Failed to fetch city names", e);
        }
    }
}
