package io.pivotal.drive.league.standings;

import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import io.pivotal.drive.mediatype.DriveResource;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.pivotal.drive.league.LeagueLinkConstants.MAIN_LINKS;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class StandingsController {

    private final StandingsService standingsService;
    private final JsonSchemaGenerator schemaGenerator;

    @GetMapping("/standings")
    @SneakyThrows
    public DriveResource<Standings> getStandings() {

        Standings standings = standingsService.getStandings();
        return DriveResource.<Standings>builder()
                .links(MAIN_LINKS)
                .data(standings)
                .schema(schemaGenerator.generateSchema(Standings.class))
                .build();
    }
}
