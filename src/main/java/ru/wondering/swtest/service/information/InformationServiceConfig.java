package ru.wondering.swtest.service.information;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix = "info")
@Data
@Validated
public class InformationServiceConfig {
    @NotEmpty
    private String dartVaderName;
    @NotEmpty
    private String leiaOrganaName;
    @NotEmpty
    private String alderaanPlanetName;
    @NotEmpty
    private String deathStarStarshipName;
}
