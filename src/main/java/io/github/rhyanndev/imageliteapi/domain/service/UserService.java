package io.github.rhyanndev.imageliteapi.domain.service;

import io.github.rhyanndev.imageliteapi.domain.AccessToken;
import io.github.rhyanndev.imageliteapi.domain.entity.User;

public interface UserService {
    User getByEmail(String email);
    User save(User user);
    AccessToken authenticate(String email, String password);

}
