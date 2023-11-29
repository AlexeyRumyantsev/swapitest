package ru.wondering.swtest.service.information;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.wondering.swtest.model.info.Information;
import ru.wondering.swtest.model.info.StarshipInformation;
import ru.wondering.swtest.model.swapi.People;
import ru.wondering.swtest.model.swapi.Planet;
import ru.wondering.swtest.model.swapi.Starship;
import ru.wondering.swtest.service.swapi.SwapiService;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

/**
 * StarWars information aggregation service.
 * It provides overview:
 * 1. Information about Starship that's used by Dart Vader
 * 2. Death Star starship crew count
 * 3. If Leia is on planet Alderaan
 */
@Component
@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InformationService {

    private final SwapiService swapiService;
    private final InformationServiceConfig config;

    public Information fetchInformation() throws IOException {
        People darthVader = swapiService.findPeopleByName(config.getDartVaderName());
        if (darthVader == null) {
            throw new InformationConstraintException("Unable to find people Darth Vader by name " +
                    config.getDartVaderName());
        }
        People leiaOrgana = swapiService.findPeopleByName(config.getLeiaOrganaName());
        if (leiaOrgana == null) {
            throw new InformationConstraintException("Unable to find people Leia Organa by name " +
                    config.getLeiaOrganaName());
        }
        Planet alderaan = swapiService.findPlanetByName(config.getAlderaanPlanetName());
        if (alderaan == null) {
            throw new InformationConstraintException("Unable to find planet Alderaan by name " +
                    config.getAlderaanPlanetName());
        }
        Starship deathStar = swapiService.findStarshipByName(config.getDeathStarStarshipName());
        if (deathStar == null) {
            throw new InformationConstraintException("Unable to find startship Death Star by name " +
                    config.getDeathStarStarshipName());
        }

        final Information information = new Information();
        // Update Dart Vader starship information
        if (darthVader.getStarships() == null || darthVader.getStarships().isEmpty()) {
            // No information, return empty object
            information.setStarship(new StarshipInformation());
        } else if (darthVader.getStarships().size() > 1) {
            // This maybe valid case, but not handled in V1
            throw new InformationConstraintException("Darth Vader has more than one Starship");
        } else {
            Starship darthVaderStarship = swapiService.getStarshipByUrl(darthVader.getStarships().get(0));
            if (darthVaderStarship == null) {
                throw new InformationConstraintException("Unable to find Darth Vader's starship by URL");
            }
            information.setStarship(StarshipInformation.builder()
                    .name(darthVaderStarship.getName())
                    .starshipClass(darthVaderStarship.getStarshipClass())
                    .model(darthVaderStarship.getModel())
                    .build());
        }

        // Update Death Star crew
        information.setCrew(Optional.ofNullable(deathStar.getCrew()).orElse(0L));

        // Update is Leia on the Alderaan
        boolean leiaOnAlderaan = Optional.ofNullable(alderaan.getResidents()).orElse(Collections.emptyList())
                .contains(leiaOrgana.getUrl());
        information.setLeiaOnThePlanet(leiaOnAlderaan);

        return information;
    }
}
