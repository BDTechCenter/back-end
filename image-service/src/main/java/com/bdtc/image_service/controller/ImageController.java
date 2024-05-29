package com.bdtc.image_service.controller;

import com.bdtc.image_service.dto.ImageUrlDto;
import com.bdtc.image_service.service.ImageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/images")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ImageUrlDto> uploadImage(
            @ModelAttribute @Valid MultipartFile image
    ) {
        ImageUrlDto urlDto = imageService.uploadImage(image);
        return ResponseEntity.ok(urlDto);
    }

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> getImage(
            @PathVariable String fileName
    ) throws IOException {
        return imageService.getImage(fileName);
    }
}
