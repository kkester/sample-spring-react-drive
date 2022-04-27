package io.pivotal.drive.mediatype;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class DriveResource<T> {
    private Map<String, DriveLink> links;
    private T data;
    private JsonNode schema;
}
