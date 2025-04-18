package io.github.rhyanndev.imageliteapi.unit.application.images;

import io.github.rhyanndev.imageliteapi.application.images.ImageMapper;
import io.github.rhyanndev.imageliteapi.application.images.ImagesController;
import io.github.rhyanndev.imageliteapi.domain.entity.Image;
import io.github.rhyanndev.imageliteapi.domain.service.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("Testes do controller de imagens")
public class ImagesControllerTest {

    @Mock
    private ImageService imageService;

    @Mock
    private ImageMapper imageMapper;

    @InjectMocks
    private ImagesController imagesController;

    private Image image;

    @BeforeEach
    void setUp() throws IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        image = new Image();
        image.setId("123");
        image.setName("Test Image");
        image.setSize(12345L);

        // Simula o comportamento do mapper
        when(imageMapper.mapToImage(any(), any(), any())).thenReturn(image);

        // Simula o comportamento do service
        when(imageService.save(any(Image.class))).thenReturn(image);
    }

    @Test
    @DisplayName("Deve retornar 201 Created quando a imagem for salva com sucesso")
    void testSaveImage() throws IOException {
        // Criando um MockMultipartFile com nome, tipo de conteúdo e conteúdo do arquivo
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",                  // nome do parâmetro do arquivo
                "testImage.jpg",         // nome do arquivo
                "image/jpeg",            // tipo de mídia
                "Test image content".getBytes()  // conteúdo do arquivo
        );
        /// MOCK ESSENCIAL: mapper.mapToImage(...) precisa retornar a imagem mockada

        when(imageMapper.mapToImage(any(), any(), any())).thenReturn(image);
        when(imageService.save(any(Image.class))).thenReturn(image);

        // Simula a chamada do método do controller
        ResponseEntity response = imagesController.save(mockFile, "Test Image", List.of("tag1", "tag2"));

        // Verifica se a resposta está como esperado
        assertEquals(201, response.getStatusCodeValue());
        verify(imageService, times(1)).save(any(Image.class));
    }
}
