package com.db2.backend.repository;

import com.db2.backend.model.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WeatherRepository extends JpaRepository<WeatherData, String> {
    @Query("SELECT w.name FROM WeatherData w")
    List<String> findAllNames();

    @Query("SELECT w FROM WeatherData w")
    List<WeatherData> getAllWeatherData();

    @Query("SELECT w FROM WeatherData w WHERE LOWER(w.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    WeatherData findByName(String name);
}
