package io.pivotal.drive.mediatype;

import org.springframework.http.MediaType;

public class DriveMediaType extends MediaType {

    public static final String APPLICATION_DRIVE_PLUS_JSON_VALUE = "application/vnd.drive+json";
    public static final MediaType APPLICATION_DRIVE_PLUS_JSON = new DriveMediaType(APPLICATION_DRIVE_PLUS_JSON_VALUE);

    public DriveMediaType(String type) {
        super(type);
    }
}
