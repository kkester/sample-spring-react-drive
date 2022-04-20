package io.pivotal.drive.view;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PlaceSummaries {
    private List<PlaceSummary> places;
}
