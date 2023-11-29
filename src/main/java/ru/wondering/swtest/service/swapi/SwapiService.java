package ru.wondering.swtest.service.swapi;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.wondering.swtest.model.swapi.People;
import ru.wondering.swtest.model.swapi.Planet;
import ru.wondering.swtest.model.swapi.Starship;

import java.io.IOException;

import static ru.wondering.swtest.service.swapi.util.RetrofitUtil.*;

/**
 * StarWars API Service, which support limited set of entities.
 */
@Component
@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SwapiService {

    private final SwapiConfig config;
    private Swapi swapiClient;

    @PostConstruct
    private void init() {
        swapiClient = createRetrofit(config.getLocale(), config.getBaseUrl(),
                config.getConnectTimeout(), config.getReadTimeout())
                .create(Swapi.class);
    }

    public People findPeopleByName(String name) throws IOException {
        return search(page -> swapiClient.searchPeople(name, page).execute(),
                people -> people != null && name.equals(people.getName()));
    }

    public Planet findPlanetByName(String name) throws IOException {
        return search(page -> swapiClient.searchPlanets(name, page).execute(),
                planet -> planet != null && name.equals(planet.getName()));
    }

    public Starship findStarshipByName(String name) throws IOException {
        return search(page -> swapiClient.searchStarships(name, page).execute(),
                starship -> starship != null && name.equals(starship.getName()));
    }

    public Starship getStarshipByUrl(String url) throws IOException {
        return checkAndUnwrapResponse(swapiClient.getStarship(url).execute());
    }

}
