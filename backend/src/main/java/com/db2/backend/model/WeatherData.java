package com.db2.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "mifiit02_weather_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private Double medianTemp;

    public Double getMedianTemp() {
        return medianTemp;
    }

    public void setMedianTemp(Double medianTemp) {
        this.medianTemp = medianTemp;
    }
}
