package io.pivotal.drive.league.standings;

import io.pivotal.drive.mediatype.DriveResource;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class StandingsDriveResource {
    private List<DriveResource<TeamStanding>> teams;
}
