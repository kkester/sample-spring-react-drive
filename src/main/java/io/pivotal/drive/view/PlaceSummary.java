package io.pivotal.drive.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class PlaceSummary {
    @JsonIgnore
    private UUID id;
    private String city;
    private States state;
    private String country;
    private LocalDate visited;
}
