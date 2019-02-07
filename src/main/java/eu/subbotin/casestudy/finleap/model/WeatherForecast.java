package eu.subbotin.casestudy.finleap.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Oleksandr Subbotin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherForecast {
    private City city;
    private WeatherStatistic weather;
}
