package ru.wondering.swtest.model.swapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * People model represents an individual person or character within the Star Wars universe.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class People {
    private String name;
    private String birthYear;
    private List<String> films;
    private String gender;
    private String hairColor;
    private String eyeColor;
    private String height;
    private String homeworld;
    private String mass;
    private String skinColor;
    private String created;
    private String edited;
    private String url;
    private List<String> species;
    private List<String> starships;
    private List<String> vehicles;
}