package io.pivotal.drive.mediatype;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.util.LinkedHashMap;
import java.util.Map;

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

    public static Map<String, DriveLink> of(String k1, DriveLink v1) {
        Map<String, DriveLink> map = new LinkedHashMap<>();
        map.put(k1, v1);
        return  map;
    }

    public static Map<String, DriveLink> of(String k1, DriveLink v1, String k2, DriveLink v2) {
        Map<String, DriveLink> map = new LinkedHashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        return  map;
    }

    public static Map<String, DriveLink> of(String k1, DriveLink v1, String k2, DriveLink v2,  String k3, DriveLink v3) {
        Map<String, DriveLink> map = new LinkedHashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        return  map;
    }

    public static Map<String, DriveLink> of(String k1, DriveLink v1, String k2, DriveLink v2,  String k3, DriveLink v3,  String k4, DriveLink v4) {
        Map<String, DriveLink> map = new LinkedHashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        return  map;
    }

    public static Map<String, DriveLink> of(String k1, DriveLink v1, String k2, DriveLink v2,  String k3, DriveLink v3,  String k4, DriveLink v4,  String k5, DriveLink v5) {
        Map<String, DriveLink> map = new LinkedHashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        map.put(k5, v5);
        return  map;
    }

    @JsonGetter("type")
    public String getType() {
        return type == null ? null : type.toString();
    }

    public DriveLink applyVariables(Object...pathVariables) {
        return toBuilder()
                .href(String.format(this.href, pathVariables))
                .build();
    }

    public DriveLink applyTitleAndVariables(String title, Object...pathVariables) {
        return toBuilder()
                .href(String.format(this.href, pathVariables))
                .title(title)
                .build();
    }
}
