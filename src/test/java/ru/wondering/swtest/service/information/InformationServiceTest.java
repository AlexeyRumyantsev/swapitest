package ru.wondering.swtest.service.information;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import ru.wondering.swtest.model.info.Information;
import ru.wondering.swtest.model.info.StarshipInformation;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InformationServiceTest {

    @Test
    void testFetchInformationSuccess() {
        final InformationServiceConfig config = createDefaultConfig();

        InformationService service = new InformationService(config);
        Information info = service.fetchInformation();

        Information expectedInfo = Information.builder()
                .starship(StarshipInformation.builder()
                        .name("TIE Advanced x1")
                        .model("Twin Ion Engine Advanced x1")
                        .starshipClass("Starfighter")
                        .build())
                .crew(342953L)
                .isLeiaOnThePlanet(true)
                .build();

        assertEquals(expectedInfo, info);
    }

    @NotNull
    private static InformationServiceConfig createDefaultConfig() {
        InformationServiceConfig config = new InformationServiceConfig();
        config.setDartVaderName("Darth Vader");
        config.setLeiaOrganaName("Leia Organa");
        config.setAlderaanPlanetName("Alderaan");
        config.setDeathStarStarshipName("Death Star");
        return config;
    }
}
