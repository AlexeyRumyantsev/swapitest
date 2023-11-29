package ru.wondering.swtest.service.swapi;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Locale;

@Component
@ConfigurationProperties(prefix = "swapi")
@Data
@Validated
public class SwapiConfig {
    /**
     * Locale to handle number format, preconfigured with US
     */
    @NotNull
    private Locale locale;
    /**
     * Base URL for StarWars API, example: https://swapi.dev/api/
     */
    @NotEmpty
    private String baseUrl;
    @NotNull
    private Integer connectTimeout;
    @NotNull
    private Integer readTimeout;
}
