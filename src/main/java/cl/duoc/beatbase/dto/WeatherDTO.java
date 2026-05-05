package cl.duoc.beatbase.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * DTO que mapea la respuesta de la Open-Meteo API.
 * Endpoint: GET https://api.open-meteo.com/v1/forecast?latitude=...&longitude=...&current_weather=true
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDTO {

    @JsonProperty("current_weather")
    private CurrentWeather currentWeather;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CurrentWeather {
        private Double temperature;
        private Double windspeed;
    }
}