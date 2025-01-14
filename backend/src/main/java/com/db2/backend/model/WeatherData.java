package com.db2.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mifiit02_weather_data")
@Data
@NoArgsConstructor
public class WeatherData {
    @Id
    @Column(name = "name")
    private String name;
    
    @Column(name = "temp")
    private double temp;
    
    @Column(name = "temp_min")
    private double tempMin;
    
    @Column(name = "temp_max")
    private double tempMax;
    
    @Column(name = "feels_like")
    private double feelsLike;
    
    private long dt;

    @Column(name = "median_temp")
    private Double median_temp;

    public WeatherData(String name, double temp, double tempMin, double tempMax, 
                      double feelsLike, long dt, Double median_temp) {
        this.name = name;
        this.temp = temp;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.feelsLike = feelsLike;
        this.dt = dt;
        this.median_temp = median_temp;
    }
}
