package ru.wondering.swtest.controller;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.wondering.swtest.model.info.Information;
import ru.wondering.swtest.model.info.StarshipInformation;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class InformationControllerTest {

    @Autowired
    private InformationController informationController;

    @Test
    @Tag("IntegrationTest")
    void successIntegrationTest() throws IOException {
        Information information = informationController.getInformation();

        Information expectedInfo = Information.builder()
                .starship(StarshipInformation.builder()
                        .name("TIE Advanced x1")
                        .model("Twin Ion Engine Advanced x1")
                        .starshipClass("Starfighter")
                        .build())
                .crew(342953L)
                .isLeiaOnThePlanet(true)
                .build();

        assertEquals(expectedInfo, information);
    }
}
