package com.db2.backend.model;

import lombok.Data;

@Data
public class OpenWeatherResponse {
    private MainData main;
    private String name;
    private long dt;

    @Data
    public static class MainData {
        private double temp;
        private double feels_like;
        private double temp_min;
        private double temp_max;
    }
}
