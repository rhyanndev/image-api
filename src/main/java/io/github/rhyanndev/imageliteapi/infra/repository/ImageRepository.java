package io.github.rhyanndev.imageliteapi.infra.repository;

import io.github.rhyanndev.imageliteapi.domain.entity.Image;
import io.github.rhyanndev.imageliteapi.domain.enums.ImageExtension;
import io.github.rhyanndev.imageliteapi.infra.repository.specs.GenericsSpecs;
import io.github.rhyanndev.imageliteapi.infra.repository.specs.ImageSpecs;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.StringUtils;

import java.util.List;

import static io.github.rhyanndev.imageliteapi.infra.repository.specs.ImageSpecs.nameLike;
import static io.github.rhyanndev.imageliteapi.infra.repository.specs.ImageSpecs.tagsLike;

// execute operations in database
public interface ImageRepository extends JpaRepository<Image, String>, JpaSpecificationExecutor<Image> {

    boolean existsByName(String name);

    /**
     *
     * @param extension
     * @param query
     * @return
     *
     * WHERE 1 = 1 ---> TRUE (THIS IS A DINAMIC QUERY, RETURN ALL IMAGES)
     *
     * SELECT * FROM IMAGE WHERE 1 = 1 AND EXTENSION = 'PNG' AND ( NAME LIKE 'NAME' OR TAGS LIKE 'QUERY')
     *
     */

    default List<Image> findByExtensionAndNameOrTagsLike(ImageExtension extension, String query){
        Specification<Image> spec = Specification.where(GenericsSpecs.conjunction());

        if(extension != null){
            spec = spec.and(ImageSpecs.extensionEqual(extension));
        }

        if(StringUtils.hasText(query)){
            Specification<Image> nameOrTagsLike = Specification.anyOf(nameLike(query), tagsLike(query));
            spec = spec.and(nameOrTagsLike);
        }

        return findAll(spec);

    }

}
