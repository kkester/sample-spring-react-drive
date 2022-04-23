package io.pivotal.drive.league.games;

import io.pivotal.drive.mediatype.DriveResource;
import io.pivotal.drive.places.view.PlaceSummary;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GameSummariesDriveResource {
    private List<DriveResource<GameSummary>> games;
}
