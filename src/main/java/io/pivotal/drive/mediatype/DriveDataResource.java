package io.pivotal.drive.mediatype;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class DriveDataResource {
    private Map<String, DriveLink> links;
    private Map<String,Object> data;
    private JsonNode schema;
}
