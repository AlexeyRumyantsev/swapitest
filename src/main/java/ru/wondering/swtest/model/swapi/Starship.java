package ru.wondering.swtest.model.swapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Starship model represents a single transport craft that has hyperdrive capability.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Starship extends Vehicle {
    private String starshipClass;
    private String hyperdriveRating;
    @JsonProperty("MGLT")
    private String mglt;

}

