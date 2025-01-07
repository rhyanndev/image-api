package io.github.rhyanndev.imageliteapi.domain.enums;

import org.springframework.http.MediaType;

import java.util.Arrays;

public enum ImageExtension {

    PNG(MediaType.IMAGE_PNG),
    GIF(MediaType.IMAGE_GIF),
    JPEG(MediaType.IMAGE_JPEG),
    WEBP(MediaType.valueOf("image/webp"));

    private MediaType mediaType;

    ImageExtension(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public static ImageExtension valueOf(MediaType mediaType){
        return Arrays.stream(values())
                .filter(ie -> ie.mediaType.equals(mediaType))
                .findFirst()
                .orElse(null);
    }
}
