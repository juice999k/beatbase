package cl.duoc.beatbase.controller;

import cl.duoc.beatbase.dto.WeatherDTO;
import cl.duoc.beatbase.controller.WeatherController;
import cl.duoc.beatbase.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/clima")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    /**
     * Devuelve el clima actual para las coordenadas indicadas.
     * Por defecto usa Santiago de Chile (lat=-33.45, lon=-70.65).
     *
     * Ejemplos:
     *   GET /api/v1/clima                          → Santiago (default)
     *   GET /api/v1/clima?lat=-41.47&lon=-72.94   → Puerto Montt
     */
    @GetMapping
    public ResponseEntity<WeatherDTO> clima(
            @RequestParam(defaultValue = "-33.45") double lat,
            @RequestParam(defaultValue = "-70.65") double lon) {

        WeatherDTO resultado = weatherService.obtenerClima(lat, lon);
        return ResponseEntity.ok(resultado);
    }
}