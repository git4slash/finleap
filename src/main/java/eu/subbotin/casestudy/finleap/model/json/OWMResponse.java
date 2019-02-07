package eu.subbotin.casestudy.finleap.model.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import eu.subbotin.casestudy.finleap.model.City;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Oleksandr Subbotin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"cod", "message", "cnt"})
public class OWMResponse {
    @JsonProperty("city")
    private City city;

    @JsonProperty("list")
    private List<OWMWeatherForecast> weatherList;
}
