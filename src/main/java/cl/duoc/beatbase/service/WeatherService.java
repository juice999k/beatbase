package cl.duoc.beatbase.service;

import cl.duoc.beatbase.dto.WeatherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WeatherService {

    @Autowired
    @Qualifier("weatherWebClient")
    private WebClient weatherWebClient;

    /**
     * Consulta el clima actual para las coordenadas dadas usando Open-Meteo.
     * La API es pública, gratuita y no requiere API Key.
     *
     * @param latitude  latitud (ej: -33.45 para Santiago)
     * @param longitude longitud (ej: -70.65 para Santiago)
     * @return WeatherDTO con temperatura, viento y más datos actuales
     */
    public WeatherDTO obtenerClima(double latitude, double longitude) {
        return weatherWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/forecast")
                        .queryParam("latitude", latitude)
                        .queryParam("longitude", longitude)
                        .queryParam("current_weather", true)
                        .build())
                .retrieve()
                .bodyToMono(WeatherDTO.class)
                .block();
    }
}