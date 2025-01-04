package io.github.rhyanndev.imageliteapi.domain.entity;

import io.github.rhyanndev.imageliteapi.domain.enums.ImageExtension;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// save images in database
@Entity
@Table(name = "tb_image")
@EntityListeners(AuditingEntityListener.class) // audit note
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column
    private String name;

    @Column
    private Long size;

    @Column
    @Enumerated(EnumType.STRING)
    private ImageExtension extension;

    @Column
    @CreatedDate
    private LocalDateTime uploadDate;

    @Column
    private String tags;

    @Column
    @Lob
    private byte[] file; // represents the image the user selected to upload

}
