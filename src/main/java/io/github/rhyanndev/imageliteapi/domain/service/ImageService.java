package io.github.rhyanndev.imageliteapi.domain.service;

import io.github.rhyanndev.imageliteapi.domain.entity.Image;
import io.github.rhyanndev.imageliteapi.domain.enums.ImageExtension;

// interface to get an optional object. Maybe yes or maybe no that has an image in the object.
import java.util.List;
import java.util.Optional;

// logical images service
public interface ImageService {
    Image save(Image image);

    Optional<Image> getById(String id);

    List<Image> search(ImageExtension extension, String query);

}
