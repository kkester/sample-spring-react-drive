package io.pivotal.drive.mediatype;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class DriveLink {
    private String rel;
    private String href;
    private String title;
    private HttpMethod method;
    private MediaType type;
    @JsonProperty(value = "$ref")
    private String ref;

    @JsonGetter("type")
    public String getType() {
        return type == null ? null : type.toString();
    }

    public DriveLink format(Object pathVariable) {
        return toBuilder()
                .href(String.format(this.href, pathVariable))
                .build();
    }
}
