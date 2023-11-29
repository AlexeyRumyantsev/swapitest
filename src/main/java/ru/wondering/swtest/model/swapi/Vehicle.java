package ru.wondering.swtest.model.swapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Vehicle model represents a single transport craft that does not have hyperdrive capability.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
    private String name;
    private String model;
    private String vehicleClass;
    private String manufacturer;
    private String costInCredits;
    private String length;
    private Long crew;
    private String passengers;
    private String maxAtmospheringSpeed;
    private String cargoCapacity;
    private String consumables;
    private ZonedDateTime created;
    private ZonedDateTime edited;
    private String url;
    private List<String> pilots;
    private List<String> films;

}
