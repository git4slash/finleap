package eu.subbotin.casestudy.finleap.service.impl;

import eu.subbotin.casestudy.finleap.model.json.OWMResponse;
import eu.subbotin.casestudy.finleap.model.json.OWMWeatherForecast;
import eu.subbotin.casestudy.finleap.model.WeatherForecast;
import eu.subbotin.casestudy.finleap.model.WeatherStatistic;
import eu.subbotin.casestudy.finleap.service.OpenWeatherMapForecastService;
import eu.subbotin.casestudy.finleap.service.WeatherForecastService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.function.TupleUtils;
import reactor.util.function.Tuple3;
import reactor.util.function.Tuples;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.function.Predicate;

/**
 * @author Oleksandr Subbotin
 * @see WeatherForecastService
 */
@Service
public class WeatherForecastServiceImpl implements WeatherForecastService {

    final private OpenWeatherMapForecastService owmService;
    final private static Predicate<OWMWeatherForecast> IS_IN_NEXT_THREE_DAYS_PREDICATE = weatherForecast ->
            weatherForecast.getLocalDateTime().isBefore(LocalDateTime.now().plus(Period.ofDays(3)));

    private Mono<Tuple3<Double, Double, Double>> tuple3Mono;
    private Mono<OWMResponse> owmResponse;

    public WeatherForecastServiceImpl(OpenWeatherMapForecastService owmService) {
        this.owmService = owmService;
    }

    @Override
    public Mono<WeatherForecast> getWeatherByCityName(String cityName) {
        return getResponse(cityName)
                .filterAndCalculate(IS_IN_NEXT_THREE_DAYS_PREDICATE)
                .formatWeatherForecast();
    }

    private Mono<WeatherForecast> formatWeatherForecast() {
        return owmResponse
                .map(OWMResponse::getCity)
                .zipWith(tuple3Mono
                            .map(TupleUtils.function(WeatherStatistic::new)),
                        WeatherForecast::new);
    }

    private WeatherForecastServiceImpl getResponse(String cityName) {
        owmResponse = owmService.getOWMResponseByCityName(cityName);
        return this;
    }

    private WeatherForecastServiceImpl filterAndCalculate(Predicate<OWMWeatherForecast> predicate) {
        tuple3Mono = owmResponse
                .flatMapIterable(OWMResponse::getWeatherList)
                .takeWhile(predicate)
                .log("converted and filtered")
                .map(OWMWeatherForecast::getMain)
                .reduce(Tuples.of(0d, 0d, 0d, 0), (current, map) ->
                        Tuples.of(
                                current.getT1() + map.getOrDefault("temp_min", 0d),
                                current.getT2() + map.getOrDefault("temp_max", 0d),
                                current.getT3() + map.getOrDefault("pressure", 0d),
                                current.getT4() + 1)
                ).map(tuple4 ->
                        Tuples.of(tuple4.getT1() / tuple4.getT4(),
                                 tuple4.getT2() / tuple4.getT4(),
                                 tuple4.getT3() / tuple4.getT4()));
        return this;
    }
}