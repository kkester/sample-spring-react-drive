package io.pivotal.drive.mediatype;

import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@Builder
public class DriveResourceList<T> {
    private Map<String, DriveLink> links;
    private Map<String, List<DriveResource<T>>> data;
    private JsonSchema schema;
}
