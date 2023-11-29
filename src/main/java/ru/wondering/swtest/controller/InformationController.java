package ru.wondering.swtest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.wondering.swtest.model.info.Information;
import ru.wondering.swtest.service.information.InformationService;

import java.io.IOException;

/**
 * Main information controller, provides aggregated view on Star Wars universe.
 * Recommended to use api/v1/information endpoint.
 * Compatibility of /information endpoint is not guaranteed and can change over time.
 */
@RestController
@Log4j2
@RequestMapping({"api/v1/information", "/information"})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InformationController {

    private final InformationService informationService;

    @GetMapping("")
    public Information getInformation() throws IOException {
        return informationService.fetchInformation();
    }
}
