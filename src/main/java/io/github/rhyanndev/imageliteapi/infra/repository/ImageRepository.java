package io.github.rhyanndev.imageliteapi.infra.repository;

import io.github.rhyanndev.imageliteapi.domain.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, String> {

}
