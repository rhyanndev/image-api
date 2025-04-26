package io.github.rhyanndev.imageliteapi.application.images;

import io.github.rhyanndev.imageliteapi.domain.entity.Image;
import io.github.rhyanndev.imageliteapi.domain.enums.ImageExtension;
import io.github.rhyanndev.imageliteapi.domain.service.ImageService;
import io.github.rhyanndev.imageliteapi.infra.repository.ImageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
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

    @Override
    public Optional<Image> getById(String id) {
        return repository.findById(id);

    }

    @Override
    public List<Image> search(ImageExtension extension, String query) {
        return repository.findByExtensionAndNameOrTagsLike(extension, query);
    }

    @Override
    public void deleteAllByIds(List<String> ids) {
        List<Image> images = repository.findAllById(ids);
        if (images.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhuma imagem encontrada");
        }
        repository.deleteAll(images);
    }
}
