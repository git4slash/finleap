package eu.subbotin.casestudy.finleap;

import eu.subbotin.casestudy.finleap.model.WeatherForecast;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@DisplayName("WeatherForecast Forecast unit tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "36000")
class WeatherForecastControllerTest {

    @Value("${api.endpoint}")
    private String apiEndpoint;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("Assuming bad request status")
    void testGetWeatherWithoutParam() {
        webTestClient.get().uri(apiEndpoint)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @DisplayName("Test wrong city name")
    void testGetWeatherWithWrongParam() {
        webTestClient.get().uri(apiEndpoint+"?cityName=Something")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    @DisplayName("Assume that return type is correct")
    void testGetWeatherByCityName() {
        webTestClient.get().uri(apiEndpoint+"?cityName=Korbach")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(WeatherForecast.class)
                .consumeWith(response -> {
                    Assertions.assertNotNull(response.getResponseBody(), "Should not be null");
                    Assertions.assertEquals(response.getResponseBody().getCity().getName(), "Korbach", "Should be equal");
                    Assertions.assertNotNull(response.getResponseBody().getWeather(), "Statistic should not be null");
                    Assertions.assertNotEquals(response.getResponseBody().getWeather().getAveragePressure(), 0d, "Pressure on earth should be more than 0");
                });
    }
}

