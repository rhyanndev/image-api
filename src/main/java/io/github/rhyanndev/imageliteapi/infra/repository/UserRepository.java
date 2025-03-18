package io.github.rhyanndev.imageliteapi.infra.repository;

import io.github.rhyanndev.imageliteapi.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    User findByEmail(String email);

}
