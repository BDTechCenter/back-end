package com.bdtc.image_service.service;

import com.bdtc.image_service.dto.ImageUrlDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageService {

    @Value("${upload.dir}")
    private String uploadDir;

    @Value("${gateway.url}")
    private String gatewayUrl;

    @Value("${spring.application.name}")
    private String applicationName;

    public ImageUrlDto uploadImage(MultipartFile image) {
        String fileName = null;
        if(image != null && !image.isEmpty()) {
            try {
                fileName = String.format("%s-%s", System.currentTimeMillis(), image.getOriginalFilename().replace(" ", "_"));
                Path filePath = Paths.get(uploadDir, fileName);
                Files.write(filePath, image.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        ImageUrlDto imageUrlDto = new ImageUrlDto(
                String.format("%s/%s/images/%s", gatewayUrl, applicationName, fileName)
        );
        return imageUrlDto;
    }

    public ResponseEntity<Resource> getImage(String fileName) throws IOException{
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
