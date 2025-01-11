package io.github.rhyanndev.imageliteapi.infra.repository.specs;

import org.springframework.data.jpa.domain.Specification;

public class GenericsSpecs {

    private GenericsSpecs(){}

        public static <T> Specification<T> conjunction(){
            return (root, queryInTheMoment, criteriaBuilder) -> criteriaBuilder.conjunction();
        }


}
