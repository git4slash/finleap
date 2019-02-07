package eu.subbotin.casestudy.finleap.service;

import eu.subbotin.casestudy.finleap.model.json.OWMResponse;
import reactor.core.publisher.Mono;

/**
 * @author Oleksandr Subbotin
 */
public interface OpenWeatherMapForecastService {
    Mono<OWMResponse> getOWMResponseByCityName(String cityName);
}
