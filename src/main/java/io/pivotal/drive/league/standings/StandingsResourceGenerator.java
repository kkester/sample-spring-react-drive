package io.pivotal.drive.league.standings;

import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import io.pivotal.drive.mediatype.DriveLink;
import io.pivotal.drive.mediatype.DriveResource;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

import static io.pivotal.drive.league.LeagueLinkConstants.MAIN_LINKS;

@Component
@RequiredArgsConstructor
public class StandingsResourceGenerator {

    private final JsonSchemaGenerator schemaGenerator;

    @SneakyThrows
    public DriveResource<StandingsDriveResource> generateDriveResource(Standings standings) {
        StandingsDriveResource standingsDriveResource = StandingsDriveResource.builder()
                .teams(standings.getTeams().stream()
                        .map(this::convert)
                        .collect(Collectors.toList()))
                .build();

        return DriveResource.<StandingsDriveResource>builder()
                .links(MAIN_LINKS)
                .data(standingsDriveResource)
                .schema(schemaGenerator.generateSchema(Standings.class))
                .build();
    }

    private DriveResource<TeamStanding> convert(TeamStanding teamStanding) {
        Map<String, DriveLink> links = Map.of(
                "team", DriveLink.builder()
                        .href("/teams/" + teamStanding.getTeamId())
                        .title("View")
                        .build()
        );
        return DriveResource.<TeamStanding>builder()
                .links(links)
                .data(teamStanding)
                .build();
    }
}
