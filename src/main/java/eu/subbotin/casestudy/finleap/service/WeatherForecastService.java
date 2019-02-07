package eu.subbotin.casestudy.finleap.service;

import eu.subbotin.casestudy.finleap.model.WeatherForecast;
import reactor.core.publisher.Mono;

/**
 * @author Oleksandr Subbotin
 */
public interface WeatherForecastService {
    Mono<WeatherForecast> getWeatherByCityName(String cityName);
}
