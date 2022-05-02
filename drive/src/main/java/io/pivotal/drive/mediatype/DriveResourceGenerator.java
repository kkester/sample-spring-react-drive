package io.pivotal.drive.mediatype;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjetland.jackson.jsonSchema.JsonSchemaGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DriveResourceGenerator {

    private final JsonSchemaGenerator schemaGenerator;

    public <T> DriveResource<T> createDriveResource(Map<String,DriveLink> links, T object) {
        return DriveResource.<T>builder()
                .links(links)
                .data(object)
                .schema(schemaGenerator.generateJsonSchema(object.getClass()))
                .build();
    }

    public <T> List<DriveResource<T>> createDriveResourceList(List<T> items, Function<T, Map<String, DriveLink>> linkFunction) {
        return items.stream()
                .map(item -> createDriveResourceItem(linkFunction.apply(item), item))
                .collect(Collectors.toList());
    }

    public <T> DriveResource<T> createDriveResourceItem(Map<String,DriveLink> links, T object) {
        return DriveResource.<T>builder()
                .links(links)
                .data(object)
                .build();
    }

    public DriveDataResource createDriveDataResource(Map<String, DriveLink> links, Map<String, Object> data, Class<?> dataType) {
        return DriveDataResource.builder()
                .links(links)
                .data(data)
                .schema(schemaGenerator.generateJsonSchema(dataType))
                .build();
    }

    public Map<String,Object> createData(Object dataObject) {
        return schemaGenerator.rootObjectMapper().convertValue(dataObject, new TypeReference<>() {});
    }
}
