package io.pivotal.places.view;

import io.pivotal.drive.mediatype.DriveResource;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PlaceSummariesDriveResource {
    private List<DriveResource<PlaceSummary>> places;
}
