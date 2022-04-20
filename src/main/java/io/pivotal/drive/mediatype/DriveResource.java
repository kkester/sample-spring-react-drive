package io.pivotal.drive.mediatype;

import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class DriveResource<T> {
    private Map<String, DriveLink> links;
    private T data;
    private JsonSchema schema;
}
