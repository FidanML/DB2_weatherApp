package com.db2.backend.controller;

import com.db2.backend.model.WeatherData;
import com.db2.backend.repository.WeatherRepository;
import com.db2.backend.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private WeatherRepository weatherRepository;

    @GetMapping("/{cityName}")
    public ResponseEntity<WeatherData> getWeatherByCity(@PathVariable String cityName) {
        WeatherData weatherData = weatherService.getWeatherDataByCity(cityName);
        return ResponseEntity.ok(weatherData);
    }

    @GetMapping("/cities")
    public ResponseEntity<List<String>> getAllCities() {
        List<String> cities = weatherService.getAllCityNames();
        return ResponseEntity.ok(cities);
    }

    @GetMapping("/all")
    public ResponseEntity<List<WeatherData>> getAllWeatherData() {
        List<WeatherData> weatherDataList = weatherRepository.getAllWeatherData();
        return ResponseEntity.ok(weatherDataList);
    }
}
