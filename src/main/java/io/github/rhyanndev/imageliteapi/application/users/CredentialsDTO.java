package io.github.rhyanndev.imageliteapi.application.users;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CredentialsDTO {

    private String email;
    private String password;

    public CredentialsDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
