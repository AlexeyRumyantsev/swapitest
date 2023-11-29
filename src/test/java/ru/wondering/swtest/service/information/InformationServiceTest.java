package ru.wondering.swtest.service.information;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import ru.wondering.swtest.model.info.Information;
import ru.wondering.swtest.model.info.StarshipInformation;
import ru.wondering.swtest.model.swapi.People;
import ru.wondering.swtest.model.swapi.Planet;
import ru.wondering.swtest.model.swapi.Starship;
import ru.wondering.swtest.service.swapi.SwapiService;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InformationServiceTest {

    @Test
    void testFetchInformationSuccess() throws IOException {
        final InformationServiceConfig config = createDefaultConfig();

        SwapiService swapiService = mock(SwapiService.class);
        when(swapiService.findPeopleByName(config.getDartVaderName())).thenReturn(
                People.builder().name(config.getDartVaderName())
                        .starships(List.of("dart-vader-starship-url")).build());
        when(swapiService.findPeopleByName(config.getLeiaOrganaName())).thenReturn(
                People.builder().name(config.getLeiaOrganaName())
                        .url("leia-url").build());
        when(swapiService.findPlanetByName(config.getAlderaanPlanetName())).thenReturn(
                Planet.builder().name(config.getAlderaanPlanetName())
                        .residents(List.of("leia-url")).build());
        when(swapiService.findStarshipByName(config.getDeathStarStarshipName())).thenReturn(
                Starship.builder().name(config.getDeathStarStarshipName())
                        .crew(342953L).build());
        when(swapiService.getStarshipByUrl("dart-vader-starship-url")).thenReturn(
                Starship.builder().name("TIE Advanced x1")
                        .model("Twin Ion Engine Advanced x1")
                        .starshipClass("Starfighter").build());

        InformationService service = new InformationService(swapiService, config);
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

    @Test
    void testExceptionOnAbsenceOfDartVader() throws IOException {
        final InformationServiceConfig config = createDefaultConfig();

        SwapiService swapiService = mock(SwapiService.class);
        when(swapiService.findPeopleByName(config.getLeiaOrganaName())).thenReturn(
                People.builder().name(config.getLeiaOrganaName())
                        .url("leia-url").build());
        when(swapiService.findPlanetByName(config.getAlderaanPlanetName())).thenReturn(
                Planet.builder().name(config.getAlderaanPlanetName())
                        .residents(List.of("leia-url")).build());
        when(swapiService.findStarshipByName(config.getDeathStarStarshipName())).thenReturn(
                Starship.builder().name(config.getDeathStarStarshipName())
                        .crew(342953L).build());
        when(swapiService.getStarshipByUrl("dart-vader-starship-url")).thenReturn(
                Starship.builder().name("TIE Advanced x1")
                        .model("Twin Ion Engine Advanced x1")
                        .starshipClass("Starfighter").build());

        InformationService service = new InformationService(swapiService, config);
        InformationConstraintException exception = assertThrows(
                InformationConstraintException.class,
                service::fetchInformation
        );

        assertEquals("Unable to find people Darth Vader by name Darth Vader",
                exception.getMessage());
    }

    @Test
    void testExceptionFetchInformationBrokenStarshipLink() throws IOException {
        final InformationServiceConfig config = createDefaultConfig();

        SwapiService swapiService = mock(SwapiService.class);
        when(swapiService.findPeopleByName(config.getDartVaderName())).thenReturn(
                People.builder().name(config.getDartVaderName())
                        .starships(List.of("dart-vader-starship-url")).build());
        when(swapiService.findPeopleByName(config.getLeiaOrganaName())).thenReturn(
                People.builder().name(config.getLeiaOrganaName())
                        .url("leia-url").build());
        when(swapiService.findPlanetByName(config.getAlderaanPlanetName())).thenReturn(
                Planet.builder().name(config.getAlderaanPlanetName())
                        .residents(List.of("leia-url")).build());
        when(swapiService.findStarshipByName(config.getDeathStarStarshipName())).thenReturn(
                Starship.builder().name(config.getDeathStarStarshipName())
                        .crew(342953L).build());
        when(swapiService.getStarshipByUrl("dart-vader-starship-url")).thenReturn(null);

        InformationService service = new InformationService(swapiService, config);

        InformationConstraintException exception = assertThrows(
                InformationConstraintException.class,
                service::fetchInformation
        );

        assertEquals("Unable to find Darth Vader's starship by URL",
                exception.getMessage());
    }

    @Test
    void testExceptionOnAbsenceOfLeia() throws IOException {
        final InformationServiceConfig config = createDefaultConfig();

        SwapiService swapiService = mock(SwapiService.class);
        when(swapiService.findPeopleByName(config.getDartVaderName())).thenReturn(
                People.builder().name(config.getDartVaderName())
                        .starships(List.of("dart-vader-starship-url")).build());
        when(swapiService.findPlanetByName(config.getAlderaanPlanetName())).thenReturn(
                Planet.builder().name(config.getAlderaanPlanetName())
                        .residents(List.of("leia-url")).build());
        when(swapiService.findStarshipByName(config.getDeathStarStarshipName())).thenReturn(
                Starship.builder().name(config.getDeathStarStarshipName())
                        .crew(342953L).build());
        when(swapiService.getStarshipByUrl("dart-vader-starship-url")).thenReturn(
                Starship.builder().name("TIE Advanced x1")
                        .model("Twin Ion Engine Advanced x1")
                        .starshipClass("Starfighter").build());

        InformationService service = new InformationService(swapiService, config);
        InformationConstraintException exception = assertThrows(
                InformationConstraintException.class,
                service::fetchInformation
        );

        assertEquals("Unable to find people Leia Organa by name Leia Organa",
                exception.getMessage());
    }

    @Test
    void testExceptionOnAbsenceOfAlderaan() throws IOException {
        final InformationServiceConfig config = createDefaultConfig();

        SwapiService swapiService = mock(SwapiService.class);
        when(swapiService.findPeopleByName(config.getDartVaderName())).thenReturn(
                People.builder().name(config.getDartVaderName())
                        .starships(List.of("dart-vader-starship-url")).build());
        when(swapiService.findPeopleByName(config.getLeiaOrganaName())).thenReturn(
                People.builder().name(config.getLeiaOrganaName())
                        .url("leia-url").build());
        when(swapiService.findStarshipByName(config.getDeathStarStarshipName())).thenReturn(
                Starship.builder().name(config.getDeathStarStarshipName())
                        .crew(342953L).build());
        when(swapiService.getStarshipByUrl("dart-vader-starship-url")).thenReturn(
                Starship.builder().name("TIE Advanced x1")
                        .model("Twin Ion Engine Advanced x1")
                        .starshipClass("Starfighter").build());

        InformationService service = new InformationService(swapiService, config);
        InformationConstraintException exception = assertThrows(
                InformationConstraintException.class,
                service::fetchInformation
        );

        assertEquals("Unable to find planet Alderaan by name Alderaan",
                exception.getMessage());
    }

    @Test
    void testExceptionOnAbsenceOfDeathStar() throws IOException {
        final InformationServiceConfig config = createDefaultConfig();

        SwapiService swapiService = mock(SwapiService.class);
        when(swapiService.findPeopleByName(config.getDartVaderName())).thenReturn(
                People.builder().name(config.getDartVaderName())
                        .starships(List.of("dart-vader-starship-url")).build());
        when(swapiService.findPeopleByName(config.getLeiaOrganaName())).thenReturn(
                People.builder().name(config.getLeiaOrganaName())
                        .url("leia-url").build());
        when(swapiService.findPlanetByName(config.getAlderaanPlanetName())).thenReturn(
                Planet.builder().name(config.getAlderaanPlanetName())
                        .residents(List.of("leia-url")).build());
        when(swapiService.getStarshipByUrl("dart-vader-starship-url")).thenReturn(
                Starship.builder().name("TIE Advanced x1")
                        .model("Twin Ion Engine Advanced x1")
                        .starshipClass("Starfighter").build());

        InformationService service = new InformationService(swapiService, config);
        InformationConstraintException exception = assertThrows(
                InformationConstraintException.class,
                service::fetchInformation
        );

        assertEquals("Unable to find startship Death Star by name Death Star",
                exception.getMessage());
    }

    @Test
    void testExceptionOnDarthOnTwoStarships() throws IOException {
        final InformationServiceConfig config = createDefaultConfig();

        SwapiService swapiService = mock(SwapiService.class);
        when(swapiService.findPeopleByName(config.getDartVaderName())).thenReturn(
                People.builder().name(config.getDartVaderName())
                        .starships(List.of("dart-vader-starship-url-1",
                                "dart-vader-starship-url-2")).build());
        when(swapiService.findPeopleByName(config.getLeiaOrganaName())).thenReturn(
                People.builder().name(config.getLeiaOrganaName())
                        .url("leia-url").build());
        when(swapiService.findPlanetByName(config.getAlderaanPlanetName())).thenReturn(
                Planet.builder().name(config.getAlderaanPlanetName())
                        .residents(List.of("leia-url")).build());
        when(swapiService.findStarshipByName(config.getDeathStarStarshipName())).thenReturn(
                Starship.builder().name(config.getDeathStarStarshipName())
                        .crew(342953L).build());

        InformationService service = new InformationService(swapiService, config);
        InformationConstraintException exception = assertThrows(
                InformationConstraintException.class,
                service::fetchInformation
        );

        assertEquals("Darth Vader has more than one Starship",
                exception.getMessage());
    }

    @Test
    void testDeathStartNoCrewInformation() throws IOException {
        final InformationServiceConfig config = createDefaultConfig();

        SwapiService swapiService = mock(SwapiService.class);
        when(swapiService.findPeopleByName(config.getDartVaderName())).thenReturn(
                People.builder().name(config.getDartVaderName())
                        .starships(List.of("dart-vader-starship-url")).build());
        when(swapiService.findPeopleByName(config.getLeiaOrganaName())).thenReturn(
                People.builder().name(config.getLeiaOrganaName())
                        .url("leia-url").build());
        when(swapiService.findPlanetByName(config.getAlderaanPlanetName())).thenReturn(
                Planet.builder().name(config.getAlderaanPlanetName())
                        .residents(List.of("leia-url")).build());
        when(swapiService.findStarshipByName(config.getDeathStarStarshipName())).thenReturn(
                Starship.builder().name(config.getDeathStarStarshipName()).build());
        when(swapiService.getStarshipByUrl("dart-vader-starship-url")).thenReturn(
                Starship.builder().name("TIE Advanced x1")
                        .model("Twin Ion Engine Advanced x1")
                        .starshipClass("Starfighter").build());

        InformationService service = new InformationService(swapiService, config);
        Information info = service.fetchInformation();

        Information expectedInfo = Information.builder()
                .starship(StarshipInformation.builder()
                        .name("TIE Advanced x1")
                        .model("Twin Ion Engine Advanced x1")
                        .starshipClass("Starfighter")
                        .build())
                .crew(0L)
                .isLeiaOnThePlanet(true)
                .build();

        assertEquals(expectedInfo, info, "Response is unexpected");
    }

    @Test
    void testDartVaderIsNotOnAnyStarship() throws IOException {
        final InformationServiceConfig config = createDefaultConfig();

        SwapiService swapiService = mock(SwapiService.class);
        when(swapiService.findPeopleByName(config.getDartVaderName())).thenReturn(
                People.builder().name(config.getDartVaderName()).build());
        when(swapiService.findPeopleByName(config.getLeiaOrganaName())).thenReturn(
                People.builder().name(config.getLeiaOrganaName())
                        .url("leia-url").build());
        when(swapiService.findPlanetByName(config.getAlderaanPlanetName())).thenReturn(
                Planet.builder().name(config.getAlderaanPlanetName())
                        .residents(List.of("leia-url")).build());
        when(swapiService.findStarshipByName(config.getDeathStarStarshipName())).thenReturn(
                Starship.builder().name(config.getDeathStarStarshipName())
                        .crew(342953L).build());

        InformationService service = new InformationService(swapiService, config);
        Information info = service.fetchInformation();

        Information expectedInfo = Information.builder()
                .starship(StarshipInformation.builder().build())
                .crew(342953L)
                .isLeiaOnThePlanet(true)
                .build();

        assertEquals(expectedInfo, info, "Response is unexpected");
    }

    @Test
    void testLeiaIsNotOnAlderaan() throws IOException {
        final InformationServiceConfig config = createDefaultConfig();

        SwapiService swapiService = mock(SwapiService.class);
        when(swapiService.findPeopleByName(config.getDartVaderName())).thenReturn(
                People.builder().name(config.getDartVaderName())
                        .starships(List.of("dart-vader-starship-url")).build());
        when(swapiService.findPeopleByName(config.getLeiaOrganaName())).thenReturn(
                People.builder().name(config.getLeiaOrganaName())
                        .url("leia-url").build());
        when(swapiService.findPlanetByName(config.getAlderaanPlanetName())).thenReturn(
                Planet.builder().name(config.getAlderaanPlanetName()).build());
        when(swapiService.findStarshipByName(config.getDeathStarStarshipName())).thenReturn(
                Starship.builder().name(config.getDeathStarStarshipName())
                        .crew(342953L).build());
        when(swapiService.getStarshipByUrl("dart-vader-starship-url")).thenReturn(
                Starship.builder().name("TIE Advanced x1")
                        .model("Twin Ion Engine Advanced x1")
                        .starshipClass("Starfighter").build());

        InformationService service = new InformationService(swapiService, config);
        Information info = service.fetchInformation();

        Information expectedInfo = Information.builder()
                .starship(StarshipInformation.builder()
                        .name("TIE Advanced x1")
                        .model("Twin Ion Engine Advanced x1")
                        .starshipClass("Starfighter")
                        .build())
                .crew(342953L)
                .isLeiaOnThePlanet(false)
                .build();

        assertEquals(expectedInfo, info, "Response is unexpected");
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
