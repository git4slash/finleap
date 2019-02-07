package eu.subbotin.casestudy.finleap.service.impl;

import eu.subbotin.casestudy.finleap.model.json.OWMResponse;
import eu.subbotin.casestudy.finleap.service.OpenWeatherMapForecastService;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author Oleksandr Subbotin
 */
@Service
public class OpenWeatherMapForecastServiceImpl implements OpenWeatherMapForecastService {
    private WebClient webClient;

    @Override
    public Mono<OWMResponse> getOWMResponseByCityName(String cityName) {
        return initWebClient("q", cityName)
                .getResponse();
    }

    private Mono<OWMResponse> getResponse() {
        return webClient
                .get()
                .retrieve()
                .bodyToMono(OWMResponse.class)
                .log("OWMResponse is parsed");
    }

    private OpenWeatherMapForecastServiceImpl initWebClient(String paramName, String paramValue) {
        webClient = WebClient.builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/forecast?" + paramName + "=" + paramValue + "&appid=85f04404cc1567f158802faacab5ce28&units=metric")
                .defaultHeader(HttpHeaders.USER_AGENT, "Spring 5 WebClient")
                .build();
        return this;
    }
}
