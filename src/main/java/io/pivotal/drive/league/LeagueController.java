package io.pivotal.drive.league;

import io.pivotal.drive.mediatype.DriveResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.pivotal.drive.league.LeagueLinkConstants.MAIN_LINKS;

@RestController
@CrossOrigin("*")
public class LeagueController {
    @GetMapping("/league")
    public DriveResource<Void> getLeague() {
        return DriveResource.<Void>builder()
                .links(MAIN_LINKS)
                .build();
    }
}
