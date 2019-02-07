package eu.subbotin.casestudy.finleap.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Oleksandr Subbotin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherStatistic {
    @JsonProperty("average-temp-day")
    private double tempMax;
    @JsonProperty("average-temp-night")
    private double tempMin;
    @JsonProperty("average-pressure")
    private double averagePressure;

}
