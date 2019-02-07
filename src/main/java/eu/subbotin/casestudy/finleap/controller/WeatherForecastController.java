package eu.subbotin.casestudy.finleap.controller;

import eu.subbotin.casestudy.finleap.model.WeatherForecast;
import eu.subbotin.casestudy.finleap.service.WeatherForecastService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author Oleksandr Subbotin
 */
@RestController
@RequestMapping("${api.endpoint}")
public class WeatherForecastController {
    final private WeatherForecastService weatherForecastService;

    public WeatherForecastController(WeatherForecastService weatherForecastService) {
        this.weatherForecastService = weatherForecastService;
    }

    @GetMapping
    Mono<WeatherForecast> getWeatherByCityName(@RequestParam String cityName) {
        return weatherForecastService.getWeatherByCityName(cityName);
    }
}
