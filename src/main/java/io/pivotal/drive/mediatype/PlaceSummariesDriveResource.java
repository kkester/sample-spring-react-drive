package io.pivotal.drive.mediatype;

import io.pivotal.drive.view.PlaceSummary;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PlaceSummariesDriveResource {
    private List<DriveResource<PlaceSummary>> places;
}
