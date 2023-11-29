package ru.wondering.swtest.service.information;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.wondering.swtest.model.info.Information;

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

    private final InformationServiceConfig config;

    public Information fetchInformation() {
        throw new NotImplementedException();
    }
}
