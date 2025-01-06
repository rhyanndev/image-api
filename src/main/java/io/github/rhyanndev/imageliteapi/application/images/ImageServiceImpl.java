package io.github.rhyanndev.imageliteapi.application.images;

import io.github.rhyanndev.imageliteapi.domain.entity.Image;
import io.github.rhyanndev.imageliteapi.domain.service.ImageService;
import io.github.rhyanndev.imageliteapi.infra.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private static final Logger log = LoggerFactory.getLogger(ImageServiceImpl.class);
    private final ImageRepository repository;

    @Override
    @Transactional
    public Image save(Image image) {
        boolean exists = repository.existsByName(image.getName());
        if(exists){
            log.info("Imagem já existe no banco");
            throw new IllegalArgumentException("A imagem já existe no banco de dados");
        }
        return repository.save(image);
    }
}
