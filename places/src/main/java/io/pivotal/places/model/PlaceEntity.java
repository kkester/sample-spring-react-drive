package io.pivotal.places.model;

import io.pivotal.places.view.States;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class PlaceEntity {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

//    @NotNull
    private String city;

//    @NotNull
    private States state;

//    @NotNull
    private String country;

    private String description;

    private boolean hasBeenVisited;

    private LocalDate visited;

    private LocalDate createdDate;

    private LocalDate lastUpdatedDate;
}
