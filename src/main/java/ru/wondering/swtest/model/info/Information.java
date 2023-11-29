package ru.wondering.swtest.model.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Store aggregated information, like starship properties, number of crew and presence of Leia on Alderaan
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Information {
    /**
     * Dart Vader's starship or empty object, if he is not linked with any.
     */
    private StarshipInformation starship;
    /**
     * Number of crew on Death Star starship
     */
    private Number crew;
    /**
     * True if Leia is on Alderaan planet
     */
    private boolean isLeiaOnThePlanet;
}
