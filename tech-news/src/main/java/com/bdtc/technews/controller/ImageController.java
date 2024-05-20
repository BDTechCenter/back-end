package com.bdtc.technews.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


@Tag(name = "Image Controller", description = "Handle all image related requests") // #spring-doc
@RestController
@RequestMapping("/images")
public class ImageController {

    // TEMPORARY CONTROLLER
    // NEEDS TO BE REPLACED TO CLOUD STORAGE, NOT LOCAL

    @Value("${upload.dir}")
    private String uploadDir;

    @Operation(summary = "get news image (static resource)", hidden = true) // #spring-doc
    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) throws IOException {
        Path imagePath = Paths.get(uploadDir).resolve(fileName);
        Resource resource = new UrlResource(imagePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
