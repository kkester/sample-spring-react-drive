package io.pivotal.drive.league.standings;

import io.pivotal.drive.mediatype.DriveResource;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class StandingsController {

    private final StandingsService standingsService;
    private final StandingsResourceGenerator resourceGenerator;

    @GetMapping("/standings")
    @SneakyThrows
    public DriveResource<StandingsDriveResource> getStandings() {
        Standings standings = standingsService.getStandings();
        return resourceGenerator.generateDriveResource(standings);
    }
}
