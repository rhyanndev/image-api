package io.github.rhyanndev.imageliteapi.unit.application.users;

import io.github.rhyanndev.imageliteapi.application.users.CredentialsDTO;
import io.github.rhyanndev.imageliteapi.application.users.UserController;
import io.github.rhyanndev.imageliteapi.application.users.UserDTO;
import io.github.rhyanndev.imageliteapi.application.users.UserMapper;
import io.github.rhyanndev.imageliteapi.domain.entity.User;
import io.github.rhyanndev.imageliteapi.domain.exception.DuplicatedTupleException;
import io.github.rhyanndev.imageliteapi.domain.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("Teste do controller de usuário")
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Test
    @DisplayName("Deve retornar 201 Created quando o usuário for salvo com sucesso")
    void shouldReturnCreatedWhenUserIsSavedSuccessfully() {
        // arrange
        UserDTO dto = UserDTO.builder()
                .name("user")
                .email("user@example.com")
                .password("12345678")
                .build();

        User user = new User();

        when(userMapper.mapToUser(dto)).thenReturn(user);

        // act
        ResponseEntity<?> response = userController.save(dto);

        // assert
        verify(userService).save(user); // Verifica se o método save foi chamado com o User esperado
        assertEquals(HttpStatus.CREATED, response.getStatusCode()); // Verifica se retornou 201
        assertNull(response.getBody()); // Verifica se não há corpo
    }

    @Test
    @DisplayName("Deve retornar 401 Unauthorized quando as credenciais forem inválidas")
    void shouldReturnUnauthorizedWhenCredentialsAreInvalid() {

        // arrange
        CredentialsDTO credentials = new CredentialsDTO("user@example.com", "wrongpass");
        when(userService.authenticate("user@example.com", "wrongpass"))
                .thenReturn(null);

        // act
        var response = userController.authenticate(credentials); // chamada direta ao método

        // assert
        assertEquals(401, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("Deve retornar 409 Conflict quando o e-mail do usuário já estiver cadastrado")
    void shouldReturnConflictWhenUserAlreadyExists() {

        // arrange
        UserDTO dto = UserDTO.builder()
                .name("user")
                .email("user@example.com")
                .password("123456")
                .build();

        User user = new User();

        // Mockando conversão do DTO para entidade
        when(userMapper.mapToUser(dto)).thenReturn(user);

        // Simulando exceção ao tentar salvar um usuário já existente
        Mockito.doThrow(new DuplicatedTupleException("Email já cadastrado"))
                .when(userService).save(user);

        // act
        ResponseEntity<?> response = userController.save(dto);

        // assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());

        @SuppressWarnings("unchecked")
        Map<String, String> body = (Map<String, String>) response.getBody();

        assertNotNull(body);
        assertTrue(body.containsKey("error"));
        assertEquals("Email já cadastrado", body.get("error"));
    }
}
